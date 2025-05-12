# Stage 1: Build the application
FROM maven:3-eclipse-temurin-23 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=builder /app/target/spring_app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]