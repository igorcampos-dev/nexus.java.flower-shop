<div align="center">

# API Flower Shop

![API](https://img.shields.io/badge/API-Flower%20Shop-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Postman](https://img.shields.io/badge/Postman-0290fcf9--6615--4929--9482--3d6375ae110e-blue)
![License](https://img.shields.io/badge/License-MIT-blue)
</div>

## Overview
A API Flower Shop é uma API de floricultura que oferece uma variedade de endpoints para gerenciar usuários e operações relacionadas a flores.

## Autenticação
Para acessar os recursos protegidos, incluindo operações CRUD de flores, certifique-se de ter a função **ADMIN**.

## Pré-requisitos

Para executar este projeto, você precisa ter instalado:

- JDK 17
- Uma versão compatível do Maven
- Para o banco de dados, você deve saber o seu ip, para usar ele no application.properties como variavel de conexao do banco de dados
- Você pode usar abrir o postman e importar todas as rotas disponiveis na pasta /endpoints-postman/Api-flowershop.postman_collection.json
- <p><strong>Configure as variáveis de ambiente</strong>:</p>
  <p><code>api.java.mail.email</code> e <code>api.java.mail.password</code> são necessárias para o projeto.</p>
  <p>Assista ao <a href="https://www.youtube.com/watch?v=bK5j-GDhq8M&feature=youtu.be">vídeo de configuração</a>.</p>


## 🛠️ Tecnologias

### Backend

- **Java:** Linguagem de programação poderosa.
- **Spring Boot:** Framework para desenvolvimento ágil.
- **Spring Security:** Autenticação e segurança de classe empresarial.
- **MySQL:** Banco de dados confiável.
- **JavaMail:** Envio de emails eficiente.
- **Lombok:** Biblioteca para redução de boilerplate.
- **Hibernate Validator:** Framework para validação de dados.

## Endpoints

### 1. Registro de Usuário

- **Endpoint:** `localhost:8085/auth/register`
- **Método:** POST
- **Autenticação:** Sem autenticação
- **Corpo da Requisição (JSON):**
  ```json
  {
      "login": "igorccampos9@gmail.com",
      "password": "224654"
  }

### 2. Login de Usuário

- **Endpoint:** `localhost:8085/auth/login`
- **Método:** POST
- **Autenticação:** Sem autenticação
- **Corpo da Requisição (JSON):**
  ```json
  {
  "login": "igorccampos9@gmail.com",
  "password": "224654"
  }

### 3. Alteração de Senha

- **Endpoint:** `localhost:8085/auth/alter-password/{email}/{hash}`
- **Método:** POST
- **Autenticação:** Bearer Token
- **Corpo da Requisição (PathVariable):**
  ```json
  localhost:8085/auth/alter-password/igorccampos9@gmail.com/dA58Cw0n8EwJdBlKtL3eSiv2ksFbg9drWx9xkuyB3AcMR3Pt

### 4. Registro de Flor

- **Endpoint:** `localhost:8085/flower-shop/register-flower/{filename}`
- **Método:** POST
- **Autenticação:** Bearer Token
- **Parâmetros de URL:**
- **{filename}:** Nome do arquivo
- **Corpo da Requisição:** Formulário com um arquivo de imagem
- **Descrição:** Registra uma nova flor na loja.

### 5. Exclusão de Flor

- **Endpoint:** `localhost:8085/flower-shop/delete/{id}`
- **Método:** DELETE
- **Autenticação:** Bearer Token
- **Parâmetros de URL:**
- **{id}:** ID da flor a ser excluída
- **Descrição:** Exclui uma flor da loja.

### 6. Visualização de Flores
- **Método:** GET
- **URL:** `localhost:8085/flower-shop/see-flowers/{filename}`
- **Autenticação:** Bearer Token
- **Parâmetros de URL:**
- **{filename}:** Nome do arquivo
- **Descrição:** Obtém informações sobre uma flor específica na loja.

### 7. Atividades de envio de E-mails
- **Método:** **GET**
- **URL:** `localhost:8085/flower-shop/activities?hash={hash}`
- **Autenticação:** Bearer Token
- **Parâmetros de Query:**
- **hash:** Hash para identificação de atividades
- **Descrição:** Obtém atividades relacionadas ao usuário.

### 8. Atualização de Flor
- **Método:** PUT
- **URL:** `localhost:8085/flower-shop/update-flower/{id}/{newFilename}`
- **utenticação:** Sem autenticação
- **Parâmetros de URL:**
- **{id}:** ID da flor a ser atualizada
- **{newFilename}:** Novo nome do arquivo
- **Descrição:** Atualiza informações sobre uma flor na loja.

### 9. Envio de Mensagem
- **Método:** POST
- **URL:** `localhost:8085/flower-shop/send-message`
- **Autenticação:** Bearer Token
- **Corpo da Requisição:**
  ```json
  {
  "email": "igorccampos9@gmail.com",
  "mensagem": "e ai igor, tudo bem?",
  "flower": "margarida",
  "hash": "dA58Cw0n8EwJdBlKtL3eSiv2ksFbg9drWx9xkuyB3AcMR3Pt"
  }


## 🐳 Executando o Projeto no Docker

Se você deseja executar este projeto em um contêiner Docker, siga estas etapas:

### 📦 Passo 1: Crie um novo JAR do projeto
Antes de começar, configure as variáveis de ambiente explicadas no início do README. No diretório raiz do projeto, execute o seguinte comando para criar um novo JAR. Certifique-se de pular os testes durante o processo:
<code>
mvn clean install -DskipTests
</code>

### 🐋 Passo 2: Atualize a base de dados
Atualize o arquivo `application.properties` com o link correto da base de dados, conforme comentado abaixo.

### ▶️ Passo 3: Inicie o contêiner Docker do MySQL
Inicie o contêiner Docker do MySQL usando o arquivo docker-compose do projeto. Execute o seguinte comando para criar o contêiner Docker com o projeto:
<code>
docker compose up
</code>
Após ele rodar e se estabilizar, prossiga para o próximo passo.

### Passo 4: Crie a imagem Docker do projeto Java
Após a geração do JAR, verifique se o arquivo Dockerfile está presente no projeto. Se estiver presente, execute o seguinte comando para criar a imagem Docker. Substitua `{nome do projeto}` pelo nome desejado para a imagem:
<code>
docker build -t {nome do projeto} .
</code>
Por exemplo:
<code>
docker build -t java-api .
</code>
Após isso, execute o seguinte comando:
<code>
docker run -p 8085:8080 {nome da sua imagem}
</code>

