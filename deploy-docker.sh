#!/bin/bash

function aguardar_conexao() {
    for i in {20..0}; do
        echo "Aguarde $i segundos para a conexão..."
        sleep 1
    done
}

if docker ps --format '{{.Names}}' | grep -q "^flower-shop-mysql$" && docker ps --format '{{.Names}}' | grep -q "^redis$"; then
    echo "MySQL e Redis estão ativos."
else
    echo "Iniciando MySQL e Redis..."
    docker-compose up -d mysql-db redis
    aguardar_conexao
fi

echo "Gerando o Jar do projeto Java..."
mvn clean install -DskipTests

echo "Construindo a imagem do projeto..."
docker build -t java-flower-api .

echo "Iniciando o projeto!"
docker run -p 8085:8085 java-flower-api
