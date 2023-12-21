#!/bin/bash

docker compose up -d

mvn clean install -DskipTests

sleep 20

docker build -t java-flower-api .

docker run -p 8085:8080 java-flower-api