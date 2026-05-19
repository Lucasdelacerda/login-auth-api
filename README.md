# 🔐 Login Auth API

API REST de autenticação desenvolvida com **Java**, **Spring Boot** e **JWT**.  
O projeto permite registrar usuários, realizar login, gerar tokens JWT e proteger rotas privadas usando Spring Security.

## 📌 Sobre o projeto

Esta API foi criada para servir como base de autenticação em aplicações web, especialmente frontends que precisam consumir endpoints seguros. Ela utiliza banco de dados em memória H2 para facilitar testes e desenvolvimento local.

### ✨ Funcionalidades

- ✅ Cadastro de usuários
- ✅ Login com email e senha
- ✅ Criptografia de senha com BCrypt
- ✅ Geração de token JWT
- ✅ Validação de token em rotas protegidas
- ✅ Configuração CORS para frontend em `http://localhost:4200`
- ✅ Console H2 habilitado para inspeção do banco em memória

## 🛠️ Tecnologias utilizadas

- ☕ Java 26
- 🍃 Spring Boot 4
- 🔒 Spring Security
- 🗄️ Spring Data JPA
- 🎟️ JWT com `java-jwt`
- 🧪 H2 Database
- ✅ Bean Validation
- 📦 Maven
- 🧰 Lombok

## 📁 Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/scrimet/login_auth_api/
│   │   ├── controllers/        # Controllers REST
│   │   ├── DTO/                # Objetos de entrada e resposta
│   │   ├── domain/user/        # Entidade User
│   │   ├── infra/security/     # JWT, filtro e configuração de segurança
│   │   ├── infra/cors/         # Configuração CORS
│   │   └── repositories/       # Repositórios JPA
│   └── resources/
│       └── application.properties
└── test/
```

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você precisa ter instalado:

- Java 26 ou compatível com a versão configurada no `pom.xml`
- Maven ou usar o Maven Wrapper incluído no projeto

### Rodando localmente

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/login-auth-api.git
cd login-auth-api
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8081
```

## ⚙️ Configurações

As principais configurações estão em `src/main/resources/application.properties`:

```properties
server.port=8081
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
api.security.token.secret=my-secret-key-from-video
```

### 🗄️ Acessando o H2 Console

Com a aplicação rodando, acesse:

```text
http://localhost:8081/h2-console
```

Use as configurações:

```text
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: 
```

## 🔑 Endpoints da API

### 📝 Registrar usuário

```http
POST /auth/register
```

#### Corpo da requisição

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "Senha@123"
}
```

#### Regras de validação da senha

- Mínimo de 6 caracteres
- Pelo menos uma letra maiúscula
- Pelo menos um caractere especial

#### Resposta de sucesso

```json
{
  "name": "João Silva",
  "token": "jwt-token-gerado"
}
```

### 🔓 Login

```http
POST /auth/login
```

#### Corpo da requisição

```json
{
  "email": "joao@email.com",
  "password": "Senha@123"
}
```

#### Resposta de sucesso

```json
{
  "name": "João Silva",
  "token": "jwt-token-gerado"
}
```

### 👤 Rota protegida

```http
GET /user
```

Para acessar essa rota, envie o token JWT no header:

```http
Authorization: Bearer seu-token-jwt
```

#### Resposta de sucesso

```text
sucesso!
```

## 🧪 Testando com cURL

### Criar usuário

```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "Senha@123"
  }'
```

### Fazer login

```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "Senha@123"
  }'
```

### Acessar rota protegida

```bash
curl -X GET http://localhost:8081/user \
  -H "Authorization: Bearer seu-token-jwt"
```

## 🧾 Scripts úteis

Executar a aplicação:

```bash
./mvnw spring-boot:run
```

Rodar os testes:

```bash
./mvnw test
```

Gerar o build:

```bash
./mvnw clean package
```

## 🔐 Observações de segurança

- Em produção, altere o valor de `api.security.token.secret`.
- Evite deixar secrets fixos no `application.properties`.
- Use variáveis de ambiente para dados sensíveis.
- Configure um banco persistente, como PostgreSQL ou MySQL, para ambientes reais.
- Revise as origens permitidas no CORS antes de publicar a API.

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.

---

Feito com 💙 usando Java e Spring Boot.
