# Step 1: Use an official Gradle image to build the application
FROM gradle:7.4.2-jdk17 AS builder

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the build.gradle and other necessary files
COPY build.gradle /app/
COPY src /app/src

# Step 4: Build the project using Gradle
RUN gradle build --no-daemon

# Step 5: Use an official OpenJDK runtime image to run the application
FROM openjdk:17-jdk-slim

# Step 6: Set the working directory for the runtime container
WORKDIR /app

# Step 7: Copy the built JAR file from the builder image
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Step 8: Expose the port that your Spring Boot app runs on (default is 8080)
EXPOSE 8080

# Step 9: Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
