# Dockerfile for Spring Boot Application

# Stage 1: Build the application using Maven
# Use an official OpenJDK runtime as a parent image. Using a specific version for reproducibility.
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files (pom.xml and .mvn if you have it)
# Copy pom.xml first to leverage Docker layer caching if only source code changes
COPY pom.xml .
# COPY .mvn/ .mvn # Uncomment if you have a .mvn directory

# Download dependencies (this layer will be cached if pom.xml doesn't change)
# RUN mvn dependency:go-offline -B # Alternative to download all dependencies

# Copy the rest of the application source code
COPY src ./src

# Package the application (skip tests for faster build in this Docker context)
# The final JAR name will be based on artifactId and version in pom.xml
# For example: spring-ai-mcp-0.0.1-SNAPSHOT.jar
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
# Use a slim JRE image for a smaller final image size.
# eclipse-temurin images are well-maintained and generally recommended.
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Define an argument for the JAR file name that will be built in the 'build' stage.
# Ensure this matches your artifactId and version from pom.xml (e.g., spring-ai-mcp-0.0.1-SNAPSHOT.jar).
ARG JAR_NAME=spring-ai-mcp-0.0.1-SNAPSHOT.jar
ARG JAR_PATH=/app/target/${JAR_NAME}

# Copy the JAR file from the build stage
COPY --from=build ${JAR_PATH} application.jar

# Expose the port the application runs on (as defined in application.properties or default 8080)
EXPOSE 8080

# Command to run the application
# Using exec form for proper signal handling
ENTRYPOINT ["java", "-jar", "/app/application.jar"]

# Optional: Add a healthcheck (requires spring-boot-starter-actuator dependency)
# HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
#   CMD curl -f http://localhost:8080/actuator/health || exit 1
