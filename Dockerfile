FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar
COPY /src/main/docker/wait-for-db.sh /usr/local/bin/
COPY src/main/resources src/main/resources

RUN chmod +x /usr/local/bin/wait-for-db.sh
ENTRYPOINT ["wait-for-db.sh", "mysql-db:3306", "--", "java", "-jar", "/app.jar"]

EXPOSE 8080
