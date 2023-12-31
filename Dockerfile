FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources src/main/resources
ENV SPRING_PROFILES_ACTIVE=dev
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8085
