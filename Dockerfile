# Use an official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from Gradle's output directory
COPY build/libs/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the Spring Boot application
CMD ["sh", "-c", "echo 'Waiting for Kafka...' && sleep 10 && java -jar app.jar"]
