# ----------------------------------------------
# Stage 1 – Build the application
# ----------------------------------------------
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set working directory inside the image
WORKDIR /app

# Copy pom.xml and download dependencies first (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the JAR file (skip tests for speed)
RUN mvn clean package -DskipTests

# ----------------------------------------------
# Stage 2 – Run the application
# ----------------------------------------------
FROM eclipse-temurin:21-jdk-alpine

# Working directory inside the final image
WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/betting-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Environment variables (can be overridden)
ENV SPRING_PROFILES_ACTIVE=default \
    JAVA_OPTS="-Xms256m -Xmx512m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]