#!/bin/sh

export TERM=xterm

# Start the Spring Boot application
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &


# Continuous monitoring and recompilation
while true; do
    watch -d -t -g "ls -lR . | sha1sum" && mvn compile
done