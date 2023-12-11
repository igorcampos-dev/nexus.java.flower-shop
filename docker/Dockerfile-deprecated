FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/templates src/main/resources/templates
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080:8080