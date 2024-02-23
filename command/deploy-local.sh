#!/bin/bash
cd ..
#/__________________________________________________/#
echo "Iniciando a aplicação!"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
