#!/bin/bash

MYSQL_CONTAINER_NAME="flower-shop-mysql"

function aguardar_conexao() {
    for i in {20..0}; do
        echo "Aguarde $i segundos para a conexão..."
        sleep 1
    done
}

if [ "$(docker ps -aq -f name=$MYSQL_CONTAINER_NAME)" ]; then
    if [ "$(docker inspect -f '{{.State.Running}}' $MYSQL_CONTAINER_NAME)" = "true" ]; then
        echo "Já existe um container MYSQL e ele já está em execução!, aguarde alguns segundos"
        aguardar_conexao
    else
        docker start $MYSQL_CONTAINER_NAME
        echo "Notamos a existência de um container com o nome, mas está inativo. Estamos iniciando ele. aguarde uns segundos."
        aguardar_conexao
    fi
else
    docker-compose -f docker-compose.yml up -d
    echo "Notamos que o container não existe. Criamos e iniciamos um, aguarde uns segundos para prosseguir."
    aguardar_conexao
fi

echo "Gerando o Jar do projeto Java..."
mvn clean install -DskipTests

echo "Construindo a imagem do projeto..."
docker build -t java-flower-api .

echo "Iniciando o projeto!"
docker run -p 8085:8080 java-flower-api