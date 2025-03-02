# Użyj obrazu Mavena jako podstawy
FROM maven:3.9-eclipse-temurin-23-alpine
# Ustaw katalog roboczy na /app
RUN mkdir /app
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline
 
COPY docker-entrypoint.sh ./
COPY src ./src
RUN chmod +x docker-entrypoint.sh