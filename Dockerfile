FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

#____________________________________/

FROM openjdk:17-alpine
ENV TERM xterm-256color
COPY --from=builder /app/target/*.jar /app.jar
COPY src/main/docker/wait-for-db.sh /usr/local/bin/
COPY src/main/resources src/main/resources
RUN chmod +x /usr/local/bin/wait-for-db.sh

ENTRYPOINT ["wait-for-db.sh", "mysql-db:3307", "--", "java", "-jar", "/app.jar"]
EXPOSE 8081