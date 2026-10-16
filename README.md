# Maya CBS

## Clean Architecture with Spring Boot 4

# Maya Core Banking System

**Maya-CBS** is an enterprise-grade **Core Banking System (CBS)** designed to model the foundations
of a modern commercial bank.

The project focuses on **clean architecture**, with the goal of building a realistic banking
platform.

This codebase is designed for Java backend developers interested in a **Microservices** application
following on **Clean Architecture** and **SOLID** principles, DDD, built with **Spring Boot 4**,
**Java 25**, and **Spring Framework 7**.

---

## Quick Info

![Java](https://img.shields.io/badge/java-25-brightgreen)
![SpringBoot](https://img.shields.io/badge/spring--boot-4.1.1-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9.13-blue)

![Coverage](https://img.shields.io/badge/jacoco%20coverage-75%25-yellow)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)

![License](https://img.shields.io/badge/license-MIT-brightgreen)
![Last Commit](https://img.shields.io/github/last-commit/arivan-amin/Spring-Clean-Microservices)
![Repo Size](https://img.shields.io/github/repo-size/arivan-amin/Spring-Clean-Microservices)
![Contributors](https://img.shields.io/github/contributors/arivan-amin/Spring-Clean-Microservices)

---

## Project Highlights

* **Commercial Banking** — accounts, customers, products, payments, lending, and other core banking
  capabilities.
* **Double-Entry Ledger** — financially consistent and auditable accounting at the heart of the
  system.
* **Clean Architecture** — domain logic isolated from frameworks, databases, messaging, and
  infrastructure.
* **Microservices** — independently deployable services with clear business boundaries.
* **Enterprise-Grade Design** — SOLID principles, DDD, idempotency, auditability, and consistency.
* **Event-Driven Integration** — asynchronous communication for workflows that benefit from
  decoupling.
* **Security & Compliance** — designed with authentication, authorization, audit trails, and
  financial controls in mind.

## Project Status

**Active Development**

The project is being built incrementally, starting with the **Ledger Service** and expanding toward
a complete commercial banking platform.

## Currently Implemented Services:

- Eureka Discovery Server
- Spring Boot API Gateway

## In Progress:

- Ledger Service

## Technologies used and their responsibility

- **Java 25**
- **Spring Boot 4**
- **Spring Cloud 2025.1.2**
- **MySQL 9.7**: Services data storage.
- **Kafka 4.1**: Event streaming for microservices.
- **Docker**
- **Eureka**: Dynamic service registry.
- **Grafana, Loki, Tempo**: Observability stack for metrics, logging, and tracing.
- **JUnit & Mockito**: Unit testing and Mocking.
- **ArchUnit**: Architecture boundaries testing and coding standards validation.
- **PMD**: Validate coding standards and best practices.
- **Pitest**: Mutation testing.
- **Swagger/OpenAPI**: API documentation.
- **Liquibase**: Database Migrations.
- **Lombok**: Cleaner code with reduced boilerplate.

---

## Architecture concepts and technical features demonstrated and implemented

- **Microservices Architecture**.
- **Clean Architecture & Clean Code**
- **Command-Query Responsibility Separation (CQRS)**
- **SOLID Principles**
- **Mutation Testing**
- **Spring Dependency Injection**
- **Aspect-Oriented Programming (AOP)**
- **Rate Limiting API**
- **Automatic API Audit Logs**: Uses Spring AOP to capture and log API calls.
- **Event-Driven Communication**: Using Kafka.
- **Robust Monitoring**: Real-time monitoring with Grafana, Loki, and Tempo.
- **Centralized Logging & Distributed Tracing**: Using Loki and Tempo.
- **Database Migrations**: Using Liquibase.
- **Dockerized Deployment**: Using Docker and Docker Compose.

---

## Clean Architecture Implementation Layers

Services in this app implement strict architectural boundaries enforced by ArchUnit rules that cause
failing unit tests when violated. In each service, there are 3 layers:

### Domain

- contains only business logic, entities, command/queries.
- Persistence (JDBC, JPA, NoSQL) or Spring code is not allowed in this layer.
- This is the innermost layer; it shouldn't know anything about the other layers.
- Any access or references to classes in the other 2 layers will cause unit test failure.

### Infrastructure

- Contains only classes related to infrastructure concerns such as data persistence, JPA, JDBC,
  Messaging, external communications that belong to this layer.
- Only calls to infrastructure classes are allowed.
- Spring configs and web classes don't belong here.
- This is the second inner layer; it can access the Domain layer.
- Any access or references to classes in the Application layer will cause unit test failure.

### Application

- Spring beans, web classes live here.
- It can access both previous layers since this is the most outward layer.
- Controllers, beans, spring config and other web related classes belong here

---

## Notable Features

### Automatic API Audit Logs

Use Spring **AOP** to capture and log API call details whenever any API in any of the services is
called.

### Clean Restful API in all services

The API follows the modern best practices in RESTful services recommendations, like using
**ResponseEntity** and returning **ProblemDetail**.

### CQRS

Command and Query Separation Principle to implement Business logic.

### Rate Limiting

Implemented in **API Gateway** using **Redis Rate Limiter**.

### ArchUnit

Validate architectural boundaries and verify adherence to best coding standards.

### PMD and Pitest

Use PMD to verify the coding style and Pitest for mutation testing.

### RestControllerAdvice

Handle specific exceptions and return a unified and standard error response instead of an exception
stack trace using Spring **ProblemDetail**. Example of API response for every error.

```
{
    "type": "https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/RuntimeException.html",
    "title": "Requested Ledger Not Found",
    "status": 404,
    "detail": "Studio by the requested id not found",
    "instance": "/ledger/protected/v1/studios/33bff7c7-77ee-4c51-9ee0-c870b437f82e",
    "category": "Resource Not Found",
    "timestamp": "2025-04-22T18:45:43.927431130Z"
}
```

### OpenAPI and Swagger Docs

Provides detailed documentation for all endpoints.

### Entity and DTO separation

Decouples core business logic from presentation using request and response POJO.

### Domain Entity and JPA separation

Domain entities have no association with JPA and are never annotated with @Entity.

## Sample audit event captured from API calls

```
        // Create Account Endpoint
        {
            "id": "6797e0215829937787277607",
            "serviceName": "account-service",
            "location": "/users/protected/v1/accounts",
            "action": "Create",
            "data": "CreateAccountRequest(name=john-doe)",
            "creationDate": "2025-01-27T14:36:01.528",
            "duration": "50ms",
            "response": "CreateAccountResponse(id=9622e5ef-5ab7-4faf-89db-7dd970ea8ef0)"
        }
```

---

## Grafana Monitoring Sample

![image](https://raw.githubusercontent.com/arivan-amin/Maya-Cbs-Spring-Microservices/master/Docs/Grafana/Grafana-Dashboard-1.png)

## Installation Guide

### Prerequisites

- **Java 25**
- **Maven**
- **Docker** & **Docker Compose**

---

### Steps to Get Started

1. **Clone the Repository:**
   ```
   git clone https://github.com/arivan-amin/maya-cbs-microservices.git
   cd maya-cbs-microservices
   ```

2. **Build and deploy the services to Docker using JIB:**
   ```
   mvn clean install
   ```

3. **Set Environment Variables (Linux/MacOS):**
   ```
   export EUREKA_USERNAME=admin
   export EUREKA_PASSWORD=admin
   ```
   ```
   *(For Windows, use `set` command)*
   ```

4. **Start the required backbone apps with Docker Compose:**
   ```
   docker compose up -d
   ```
5. **Start services (Ledger) from IDE or Maven**

# Access the Services

## API Gateway

[http://localhost:8080](http://localhost:8080)

## Eureka Dashboard

#### Username: admin

#### Password: admin

[http://localhost:8080/eureka/web](http://localhost:8080/eureka/web)

## API Documentation

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Grafana Dashboard

#### Import pre-built dashboard JSON configuration from the `Docker/grafana/` folder

[http://localhost:3000/dashboards](http://localhost:3000/dashboards)

---

## Testing

- **Run Unit and Integration Tests:**
   ```bash
   mvn test
   ```

---

## Microservices Overview

- **Discovery Server**: Dynamic service discovery and registry.
- **API Gateway**: Centralized entry point for routing and security.
- **Core Module**: Shared utilities and functionality.
- **Ledger Service**: double-entry book-keeping.

---

## Contributing

We welcome contributions! Fork the repository, create a new branch, and submit a pull request.

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more
details.

---

## Contact

For questions or inquiries:

- **Name:** Arivan Amin
- **Email:** [arivanamin@gmail.com](mailto:arivanamin@gmail.com)
