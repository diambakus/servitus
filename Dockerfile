# ---- Runtime stage ----
FROM openjdk:21-jdk-slim
LABEL authors="adruida"

# Create app user
RUN addgroup --system orakuma && adduser --system orakuma --ingroup orakuma
USER orakuma:orakuma

# Copy built jar from builder stage
COPY  /target/*.jar /opt/app/servitus.jar

CMD ["java", "-showversion", "-jar", "/opt/app/servitus.jar"]