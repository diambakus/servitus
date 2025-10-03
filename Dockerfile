# ---- Build stage ----
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Runtime stage ----
FROM openjdk:21-jdk-slim
LABEL authors="adruida"

EXPOSE 8080

# Create app user
RUN addgroup --system orakuma && adduser --system orakuma --ingroup orakuma
USER orakuma:orakuma

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar /opt/app/servitus.jar

CMD ["java", "-showversion", "-jar", "/opt/app/servitus.jar"]
