<h1 align="center" style="color: blue;">
  Floricultura Online
</h1>

<div align="center">
  <img src="https://img.shields.io/badge/Linguagem-Java-red" alt="Java">
  <img src="https://img.shields.io/badge/Frontend-React-blue" alt="React">
  <a href="https://github.com/seu-usuario/seu-repositorio/releases">
    <img src="https://img.shields.io/badge/Vers%C3%A3o-1.0-blue.svg" alt="Versão">
  </a>
  <a href="https://instagram.com/igor_de_campos_">
    <img src="https://img.shields.io/badge/Instagram-igor_de_campos__-brightgreen.svg" alt="Instagram">
  </a>
</div>

<p align="center">
  Bem-vindo ao Projeto de Floricultura, uma plataforma de envio de flores e mensagens personalizadas.
</p>

<h2 align="center">🌟 Recursos Principais</h2>

<p align="center">
  <strong>Autenticação Segura:</strong> Utilizamos o Spring Security para garantir que seus dados estejam protegidos.<br><br>
  <strong>Envio de Mensagens Personalizadas:</strong> Crie mensagens especiais para acompanhar suas flores.<br><br>
</p>

<h2 align="center">🚀 Início Rápido</h2>
<div>
  <div align="center">
    <h3>Configurando o Projeto:</h3>
  </div>

  <p>1. <strong>Clone o repositório</strong>:</p>
  <code>git clone https://github.com/igorcampos-dev/flower_shop-api-java</code>

  <div align="center">
    <h4>Configuração do Ambiente:</h4>
  </div>

  <p>2. <strong>Crie um esquema no MySQL chamado "floricultura"</strong>.</p>
  <p>Certifique-se de ter o MySQL instalado.</p>

  <p>3. <strong>Configure as variáveis de ambiente</strong>:</p>
  <p><code>api.java.mail.email</code> e <code>api.java.mail.password</code> são necessárias para o projeto.</p>
  <p>Assista ao <a href="https://www.youtube.com/watch?v=bK5j-GDhq8M&feature=youtu.be">vídeo de configuração</a>.</p>

  <p>4. <strong>Acesse a aplicação</strong> em seu navegador:</p>
  <p><a href="http://localhost:8080/auth/login">http://localhost:8080/auth/login</a></p>
</div>


<h2 align="center">🛠️ Tecnologias</h2>

<h3 align="center">Backend</h3>

<p align="center">
  <strong>Java:</strong> Linguagem de programação poderosa.<br><br>
  <strong>Spring Boot:</strong> Framework para desenvolvimento ágil.<br><br>
  <strong>Spring Security:</strong> Autenticação e segurança de classe empresarial.<br><br>
  <strong>MySQL:</strong> Banco de dados confiável.<br><br>
  <strong>JavaMail:</strong> Envio de emails eficiente.<br><br>
  <strong>Lombok:</strong> Biblioteca para redução de boilerplate.<br><br>
  <strong>Hibernate Validator:</strong> Framework para validação de dados.<br><br>
</p>

<h3 align="center">Frontend</h3>

<p align="center">
  <strong>React:</strong> Biblioteca JavaScript para construção de interfaces.<br><br>
</p>

<h2 align="center">🔗 Endpoints</h2>

<h3 align="center">Autenticação e Registro</h3>

- **Tela de Login (Pendências do Frontend):**

  - **Endpoint:** `POST /auth/login`
  - **Descrição:** Responsável por efetuar o login, recebendo informações do front-end em formato JSON.

- **Tela de Registro (Pendências do Frontend):**

  - **Endpoint:** `POST /auth/register`
  - **Descrição:** Possui um endpoint que recebe informações do usuário do front-end em formato JSON.

<h3 align="center">Menu Principal -(pendente fazer front-end)</h3>

- **Ver Flores (Pendências do Frontend):**

  - **Endpoint:** `GET /flower-shop/see-flowers`
  - **Descrição:** A página renderiza as flores disponíveis no banco de dados, permitindo que o usuário escolha a flor que deseja enviar.

- **Enviar uma Flor (Pendências do Frontend):**

  - **Endpoint:** `POST /flower-shop/send-message`
  - **Descrição:** Responsável por receber mensagens, processá-las e utilizar o JavaMail para enviar uma mensagem ao destinatário.

- **Atividades de Email (Pendências do Frontend):**

  - **Endpoint:** `GET /flower-shop/activities`
  - **Descrição:** Fornecerá informações ao frontend, como horário de envio e remetente, indicando as ações realizadas pelo usuário no site.

- **Suporte/Sobre Nós (Pendências do Frontend):**

- **Endpoint:** Não haverá, tudo é feito no frontend.

<h3 align="center">Endpoint de Admin (Ainda Pendente)</h3>

- **Endpoint:** `POST /flower-shop/admin`
- **Descrição:** Este endpoint é protegido por segurança com o Spring Security e é responsável por realizar funções com as flores cadastradas, acessível apenas pelo administrador.

- **Manutnção:** `está em manutenção o docker`
- 
<h2 align="center">🐳 Executando o Projeto no Docker</h2>

Se você deseja executar este projeto em um contêiner Docker, siga estas etapas:

📦 **Passo 1: Crie um novo JAR do projeto (configure antes as variáveis de ambiente explicado no início do readme)**

No diretório raiz do projeto, execute o seguinte comando para criar um novo JAR. Certifique-se de pular os testes durante o processo:

 <code> mvn clean install -DskipTests</code><br><br>

🐋 Passo 3: Troque a database da base de dados

  <code> application.properties </code><br><br> 

*pro link correto comentado abaixo

🐋 Passo 2: Crie a imagem Docker

Após a geração do JAR, verifique se o arquivo Dockerfile está presente no projeto. Se estiver presente, execute o seguinte comando para criar a imagem Docker. Substitua {nome do projeto} pelo nome desejado para a imagem:

 <code> docker build -t {nome do projeto} .</code><br><br>

Exemplo:

 <code> docker build -t java-api .</code><br><br>

▶️ Passo 3: Inicie o contêiner Docker com o aplicativo (arquivo docker-compose) do projeto)

Execute o seguinte comando para criar o contêiner Docker com o projeto.

  <code> docker compose up </code><br><br>

