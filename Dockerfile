# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container (use exact JAR name)
COPY target/Auth-service-Shopzit-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
