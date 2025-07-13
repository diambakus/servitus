FROM openjdk:21-jdk-slim
LABEL authors="adruida"
RUN addgroup --system orakuma && adduser --system orakuma --ingroup orakuma
USER orakuma:orakuma
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/app/servitus.jar
CMD ["java", "-showversion", "-jar", "/opt/app/servitus.jar"]