#!/bin/sh

# Exit on any error
set -e

# Cleanup function
cleanup() {
    echo "Shutting down..."
    kill $(jobs -p)
    exit 0
}

# Set up trap for cleanup
trap cleanup SIGTERM SIGINT

# Function to start the Spring Boot application
start_spring_boot() {
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &
    SPRING_PID=$!
}

# Start Spring Boot application in debug mode
start_spring_boot

# Initialize last modified time
LAST_MODIFIED=$(find . -type f -name "*.java" -exec stat -c %Y {} \; | sort -nr | head -n1)

# Watch for file changes
while true; do
    CURRENT_MODIFIED=$(find . -type f -name "*.java" -exec stat -c %Y {} \; | sort -nr | head -n1)

    if [ "$CURRENT_MODIFIED" != "$LAST_MODIFIED" ]; then
        echo "Changes detected, recompiling..."
        kill $SPRING_PID
        mvn compile
        LAST_MODIFIED=$CURRENT_MODIFIED
        start_spring_boot
    fi

    sleep 2
done
