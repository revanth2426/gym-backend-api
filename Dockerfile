# Use a base image with a Java Development Kit (JDK) installed.
# eclipse-temurin provides OpenJDK builds. Use Java 17, which matches your project.
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files.
# The `pom.xml` first to allow Docker to cache dependencies more effectively.
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application (this step will run inside the container during Docker build)
# `-Dmaven.test.skip=true` skips tests as they are usually not needed in the final image.
# `-Dspring-boot.repackage.skip=true` prevents Spring Boot from creating the executable JAR
# in the traditional way, as we'll build it in two stages for better caching.
RUN mvn clean package -Dmaven.test.skip=true

# The `target` directory now contains your built JAR.
# The default JAR name is usually artifactId-version.jar
# Example: gym-management-system-0.0.1-SNAPSHOT.jar
# Replace 'gym-management-system-0.0.1-SNAPSHOT.jar' with your actual JAR filename!
ARG JAR_FILE=target/gym-management-system-0.0.1-SNAPSHOT.jar

# Copy the built JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the port your Spring Boot application runs on (default is 8080 or 8088 as configured)
# Ensure this matches your server.port in application.properties (which is 8088)
EXPOSE 8088

# Command to run the application when the container starts
ENTRYPOINT ["java","-jar","app.jar"]