# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle /app/

# Copy the gradle wrapper files
COPY gradlew /app/gradlew
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Add execute permission to gradlew
RUN chmod +x ./gradlew

# Verify that gradlew works
RUN ./gradlew --version

# clean previous build if any
RUN ./gradlew clean

# Build the application
RUN ./gradlew bootJar

# Copy the generated JAR file to the final image
COPY build/libs/*.jar app.jar

# Expose port 8088
EXPOSE 8088

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
