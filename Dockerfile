# Test Runner Dockerfile
# Builds the Maven project and runs tests against the Dockerized Appium server

FROM maven:3.9-eclipse-temurin-11 AS builder

WORKDIR /app

# Copy POM first to cache dependencies
COPY pom.xml .
RUN mvn dependency:resolve -B

# Copy all project sources
COPY src/ src/
COPY config/ config/
COPY apps/ apps/
COPY testng.xml .

# Compile the project
RUN mvn compile test-compile -B

# Run tests (entry point)
# The APPIUM_HOST env variable is set by docker-compose
CMD ["mvn", "test", "-B"]
