#!/bin/bash

cd ..

function aguardar_conexao() {
    for i in {25..0}; do
        echo "Aguarde $i segundos para a conexão..."
        sleep 1
    done
}

if docker ps --format '{{.Names}}' | grep -q "^flower-shop-mysql$" && docker ps --format '{{.Names}}' | grep -q "^redis$"; then
    echo "MySQL e Redis estão ativos."
else
    echo "Iniciando MySQL e Redis..."
    docker-compose up -d mysql-db redis
fi

aguardar_conexao

echo "Iniciando a aplicação!"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
