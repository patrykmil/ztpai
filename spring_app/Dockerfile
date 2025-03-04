FROM eclipse-temurin:23-jdk-alpine

# Install required packages
RUN apk add --no-cache maven inotify-tools dos2unix

WORKDIR /app

# Copy the entrypoint script first
COPY docker-entrypoint.sh .

# Ensure the script has Unix line endings and is executable
RUN dos2unix docker-entrypoint.sh && \
    chmod +x docker-entrypoint.sh

# Copy the rest of the project files
COPY pom.xml .
COPY src ./src/

# Expose ports for the application and debug
EXPOSE 8080
EXPOSE 5005

# Use shell form to ensure proper script execution
ENTRYPOINT ["sh", "/app/docker-entrypoint.sh"]