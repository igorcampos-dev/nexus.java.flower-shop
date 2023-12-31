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

echo "Iniciando a aplicação!"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
