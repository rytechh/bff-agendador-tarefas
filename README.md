# 🚀 Task Scheduler Ecosystem — BFF & Microservices Architecture

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)](https://www.mongodb.com/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-red.svg)](https://spring.io/projects/spring-security)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)]()

Um ecossistema distribuído baseado em **Arquitetura de Microsserviços** e orientado pelo padrão **BFF (Backend For Frontend)**. A solução foi projetada para gerenciamento de usuários, agendamento de tarefas e envio de notificações, combinando autenticação stateless, comunicação síncrona inter-serviços e persistência poliglota.

---

## 📌 Visão Geral do Ecossistema

A aplicação é dividida em um **BFF (Backend For Frontend)** centralizador e **3 Microsserviços especializados**:

1. **BFF (bff-agendador-tarefas):** Ponto único de entrada da aplicação. Responsável pelo roteamento, validação de contratos, documentação OpenAPI/Swagger e segurança via **Spring Security + JWT**.
2. **usuario:** Microsserviço responsável pela gestão cadastral de usuários, endereços, telefones e integração síncrona com a API externa do **ViaCEP** (Persistência em **PostgreSQL**).
3. **Agendador-Tarefas:** Microsserviço focado na criação, controle e ciclo de vida de agendamentos (Persistência em **MongoDB**).
4. **Notificação:** Microsserviço especialista no envio síncrono de notificações via e-mail sobre o status das tarefas.

### 💡 Destaques de Engenharia
* **Pattern Backend For Frontend (BFF):** Desacoplamento entre a camada exposta para clientes e os microsserviços internos de domínio.
* **Persistência Poliglota (*Polyglot Persistence*):** Utilização do **PostgreSQL** para dados relacionais/cadastrais e **MongoDB** para o modelo de documentos flexível de tarefas.
* **Comunicação Síncrona Inter-Serviços:** Roteamento e consumo transparente dos microsserviços internos via cliente HTTP REST.
* **Consistência Transacional:** Controle estrito de alterações cadastrais e integridade de dados usando `@Transactional`.
* **Segurança Centralizada:** Validação e propagação de Tokens JWT no BFF para proteção dos endpoints.
* **Consumo de APIs Externas:** Integração com o serviço ViaCEP para autopreenchimento de endereços.

---

## 🏗️ Arquitetura do Sistema

```text
                               ┌───────────────────────────┐
                               │   CLIENTE / SWAGGER UI    │
                               └─────────────┬─────────────┘
                                             │
                                             ▼
                               ┌───────────────────────────┐
                               │   BFF AGENDADOR TAREFAS   │
                               │  (Auth JWT • Validation)  │
                               └─────────────┬─────────────┘
                                             │ (HTTP / REST)
               ┌─────────────────────────────┼─────────────────────────────┐
               │                             │                             │
               ▼                             ▼                             ▼
┌────────────────────────────┐ ┌────────────────────────────┐ ┌────────────────────────────┐
│         MS-USUÁRIO         │ │   MS-AGENDADOR-TAREFAS     │ │       MS-NOTIFICAÇÃO       │
│  (Gestão Cadastral/ViaCEP) │ │ (Ciclo de Vida de Tarefas) │ │  (Envio de E-mails/Notif.) │
└──────────────┬─────────────┘ └──────────────┬─────────────┘ └────────────────────────────┘
               │                              │
               ▼                              ▼
┌────────────────────────────┐ ┌────────────────────────────┐
│    PostgreSQL Database     │ │      MongoDB Database      │
└────────────────────────────┘ └────────────────────────────┘

```

---

## 🛠️ Tech Stack & Ferramentas

* **Linguagem:** Java 21 (Records, Pattern Matching)
* **Framework Core:** Spring Boot 3.x
* Spring Web (REST APIs)
* Spring Security & JWT
* Spring Data JPA & Spring Data MongoDB
* OpenFeign / RestTemplate (Comunicação Síncrona)


* **Bancos de Dados:**
* **PostgreSQL:** Armazenamento relacional de usuários, endereços e telefones.
* **MongoDB:** Armazenamento NoSQL para tarefas e histórico de agendamentos.


* **Integrações Externas:** API ViaCEP & Servidor SMTP para e-mails.
* **Documentação:** OpenAPI 3 / Swagger UI
* **DevOps:** Docker e Docker Compose

---

## 📑 Documentação da API (Endpoints do BFF)

### 👤 Módulo de Usuários & Autenticação (`/usuario`)

| Método | Endpoint | Descrição | Requer Auth | Status HTTP |
| --- | --- | --- | --- | --- |
| `POST` | `/usuario` | Cadastra um novo usuário no sistema | ❌ | `200 OK` / `409 Conflict` |
| `POST` | `/usuario/login` | Realiza autenticação e retorna o Token JWT | ❌ | `200 OK` / `401 Unauthorized` |
| `GET` | `/usuario` | Busca dados do usuário pelo e-mail | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `PUT` | `/usuario` | Atualiza dados cadastrais do usuário | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `DELETE` | `/usuario/{email}` | Remove um usuário pelo e-mail | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `POST` | `/usuario/endereco` | Cadastra um novo endereço para o usuário | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `PUT` | `/usuario/endereco` | Atualiza endereço existente por ID | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `POST` | `/usuario/telefone` | Cadastra um novo telefone para o usuário | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `PUT` | `/usuario/telefone` | Atualiza telefone existente por ID | 🔒 | `200 OK` / `401 Unauthorized` / `403 Forbidden` |
| `GET` | `/usuario/endereco/{cep}` | Consulta dados de endereço via API ViaCEP | ❌ | `200 OK` / `400 Bad Request` |

---

### ⏱️ Módulo de Agendamento de Tarefas (`/tarefas`)

| Método | Endpoint | Descrição | Requer Auth | Status HTTP |
| --- | --- | --- | --- | --- |
| `POST` | `/tarefas` | Cria e agenda uma nova tarefa | 🔒 | `201 Created` |
| `GET` | `/tarefas` | Lista todas as tarefas do usuário autenticado | 🔒 | `200 OK` |
| `GET` | `/tarefas/{id}` | Detalha uma tarefa específica pelo ID | 🔒 | `200 OK` / `404 Not Found` |
| `PATCH` | `/tarefas/{id}/status` | Altera status da tarefa (`PENDENTE`, `NOTIFICADO`, `CANCELADO`) | 🔒 | `200 OK` |
| `DELETE` | `/tarefas/{id}` | Remove um agendamento do MongoDB | 🔒 | `204 No Content` |

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

* **Java 21 JDK**
* **Docker & Docker Compose**

### 1. Clonar o repositório

```bash
git clone [https://github.com/rytechh/bff-agendador-tarefas.git](https://github.com/rytechh/bff-agendador-tarefas.git)
cd bff-agendador-tarefas

```

### 2. Subir os Bancos de Dados via Docker Compose

```bash
docker-compose up -d

```

### 3. Executar a Aplicação

```bash
./gradlew bootRun

```

### 4. Acessar a Documentação Interativa

* **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 🚀 Próximos Passos (Roadmap)

Evoluções planejadas para o ecossistema:

* [ ] Implementação de broker de mensageria (RabbitMQ/Kafka) para transformar a comunicação com o **MS-Notificação** em assíncrona.
* [ ] Adição de métricas e observabilidade nos microsserviços utilizando Prometheus e Grafana.
* [ ] Implementação de Service Discovery (Eureka) e API Gateway.

```
