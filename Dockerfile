# Stage 1: Build the application
FROM openjdk:21-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and project files
COPY gradlew gradlew.bat build.gradle.kts ./
COPY gradle/ gradle/
COPY src/ src/

# Grant execute permission for Gradle wrapper
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the application with a lightweight JRE image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the Spring Boot application
CMD ["sh", "-c", "echo 'Waiting for Kafka...' && sleep 10 && java -jar app.jar"]