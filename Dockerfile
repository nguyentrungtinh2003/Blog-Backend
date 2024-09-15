# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/blog_backend_http-0.0.1-SNAPSHOT.jar /app/blog_backend_http-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "blog_backend_http-0.0.1-SNAPSHOT.jar"]
