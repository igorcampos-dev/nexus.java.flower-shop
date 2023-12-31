<div align="center">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-T4/uDyXN8KOgoN4p3ZUZl3zrm+FjI5mZ0enAeWI+Re6qk05hAA1a1TpVOL6KBo+aq/47viKiaefWi0U3Ol9FwDQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />


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
- Você pode usar abrir o postman e importar todas as rotas disponiveis no arquivo /endpoints-postman/Api-flowershop.postman_collection.json
- <p><strong>Configure as variáveis de ambiente no aplication.yml, por padrão, o projeto está rodando em ambiente dev, ou seja,configure o application-dev</strong>:</p>
  <p><code>api.java.mail.email</code> e <code>api.java.mail.password</code> são necessárias para o projeto, incluindo o seu ip para os containers (ou localhost em alguns casos)</p>
  <p>Assista ao <a href="https://www.youtube.com/watch?v=bK5j-GDhq8M&feature=youtu.be">vídeo de configuração</a>.</p>

## 🚪 Portas

 ### Porta Padrão : localhost:8085.


## 🛠️ Tecnologias

### Backend

- **Java:** <i class="fab fa-java"></i> Linguagem de programação poderosa.
- **Spring Boot:** <i class="fa-solid fa-bolt"></i> Framework para desenvolvimento ágil.
- **Spring Security:** <i class="fas fa-shield-alt"></i> Autenticação e segurança de classe empresarial.
- **MySQL:** <i class="fas fa-database"></i> Banco de dados confiável.
- **JavaMail:** <i class="fas fa-envelope"></i> Envio de emails eficiente.
- **Lombok:** <i class="fas fa-magic"></i> Biblioteca para redução de boilerplate.
- **Hibernate Validator:** <i class="fas fa-check-circle"></i> Framework para validação de dados.
- **Swagger OpenAPI:** <i class="fas fa-book"></i> Biblioteca de documentação.


## Usando o Swagger

**Acesse o Swagger em: [http://localhost:8085/swagger-ui/index.html#](http://localhost:8085/swagger-ui/index.html#)**

**Preste bastante atenção aqui!** Para acessar as rotas autenticadas, siga os passos abaixo:

1. 📝 Registre uma conta na rota de registro, fornecendo dados reais.
2. 🔄 Observe cuidadosamente a resposta HTTP recebida após o registro.
3. 🔐 Efetue o login usando as credenciais recém-criadas.
4. 🎫 Após o login bem-sucedido, obtenha o token JWT.
5. **🔒 No Swagger, clique no ícone "Authorize".**
6. **🚀 Insira e autorize o seu token JWT correto.**

**ATENÇÃO!** As rotas do CRUD de flores requerem um usuário que tenha a role de admin. Certifique-se de verificar o log do Spring Boot após o login, pois no final será gerado um usuário admin para testar essas rotas, faça o login com ele e autentique o jwt token.

## 🐳 Executando o Projeto no Docker

Se você deseja executar este projeto em um contêiner Docker, siga estas etapas:

### 📦 Execute o Script de Deploy
No diretório raiz do projeto, execute o seguinte comando para iniciar o projeto em um contêiner Docker. Este script compilará o projeto, iniciará o contêiner Docker do MySQL e executará o projeto Java no contêiner:
```bash
./deploy-docker.sh
```

Ao executar,por padrão, você estará ativando o ambiente dev configurado no dockerfile

## 🚀 Executando o Projeto Localmente

Para executar este projeto localmente, siga estas etapas:

### 📦 Execute o Script de Deploy Local

No diretório raiz do projeto, execute o seguinte comando para iniciar o projeto localmente:

```bash
./deploy-local.sh
```

Ao executar,por padrão, você estará ativando o ambiente dev configurado no comando sh





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
  ```text
  localhost:8085/auth/alter-password/igorccampos9@gmail.com/dA58Cw0n8EwJdBlKtL3eSiv2ksFbg9drWx9xkuyB3AcMR3Pt
  ```

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
