#!/bin/bash
cd ..
#/____________________________________________/#
echo "Gerando o Jar do projeto Java..."
mvn clean install -DskipTests
#/____________________________________________/#
echo "Executa o Projeto por completo"
docker compose up
