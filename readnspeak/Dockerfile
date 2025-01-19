# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from your build to the container
COPY build/libs/ReadNSpeak-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port your app runs on (e.g., 8080 for Spring Boot)
EXPOSE 8080
