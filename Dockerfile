# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine as builder

# Set the working directory
WORKDIR /app

# Copy Maven pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the JAR file
# -Dmaven.test.skip=true skips tests
# -Dspring-boot.build-info.skip=true can be added to skip build info generation if not needed
RUN mvn clean package -Dmaven.test.skip=true

# Stage 2: Create the final image
# Use a smaller base image for the final application, only containing JRE (Java Runtime Environment)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
# Replace 'gym-management-system-0.0.1-SNAPSHOT.jar' with your actual JAR filename!
COPY --from=builder /app/target/gym-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (matches server.port in application.properties)
EXPOSE 8088

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]