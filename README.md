# Desafio de Programação - Itaú Unibanco (Júnior)

Este sistema foi desenvolvido para processar transações financeiras e fornecer estatísticas com base em um intervalo de tempo informado. As transações são armazenadas em memória e a aplicação expõe endpoints RESTful documentados com Swagger.

---

## 🛠️ Tecnologias Utilizadas

- Intellij IDEA
- Java 17
- Spring Boot
- Gradle
- Swagger/OpenAPI (Springdoc)
- Docker

---

## 🚀 Como Executar o Projeto

### 1. Pré-requisitos

Antes de começar, você precisa ter instalado:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git (opcional)](https://git-scm.com/downloads)
- [Docker (opcional)](https://www.docker.com/products/docker-desktop/)

### 2. Clone o Repositório

```bash
git clone https://github.com/lucasoliveira04/challenge-itau-jr.git
cd challenge-itau-jr
```

### 3. Ou baixe o repositório em formato `.zip` e extraia.

### 4. Compile o Projeto

```bash
./gradlew build
```

### 5. Execute a Aplicação

```bash
./gradlew bootRun
```

### 6. Acesse a aplicação:

```bash
http://localhost:8080
```

---

## 📘 Acessar a Documentação da API

Você pode acessar a interface Swagger da API em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📎 Exemplos de Uso com `curl`

### Criar uma transação

```bash
    curl -X POST http://localhost:8080/transacao \
  -H "Content-Type: application/json" \
  -d '{
        "valor": 100.0,
        "dataHora": "2025-04-08T15:30:00Z"
      }'

```

### Obter estatísticas
```bash
    curl -X GET http://localhost:8080/estatisticas
```
---

## 🧪 Testes

Para executar os testes:

```bash
./gradlew test
```

---

## 🐳 Executar com Docker (alternativa)

Caso prefira rodar com Docker:

```bash
docker build -t challenge-itau-jr .
docker run -p 8080:8080 challenge-itau-jr
```

---

## 📂 Estrutura do Projeto

```
├── controller       # Endpoints REST
├── dto              # Objetos de transferência de dados
├── exception        # Exceções e tratadores de erro
├── service          # Lógica de negócio
├── modal            # Modelos e repositório em memória
├── repository       # Repositório em memória
├── ChallengeItauJuniorApplication.java
```
