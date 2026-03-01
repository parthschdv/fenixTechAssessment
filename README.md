# Multi-Tenant Client API

Spring Boot backend supporting multi-tenant orders and fulfillments.

## Tech Stack

- Java 17
- Spring Boot 3.4
- Spring Data JPA
- MySQL 8
- Maven
- Swagger (SpringDoc)

## Architecture

Controller → Service → Repository → DB

Multi-tenant isolation enforced using `tenant_id` on all tables.

Fulfillment inherits tenant from Order (never client supplied).

## How to Run

1. Configure application.yml

2. Run:
   mvn spring-boot:run

Swagger:
http://localhost:8080/swagger-ui.html

## Features

- Order CRUD
- Order Upsert
- Fulfillment CRUD
- Pagination
- Filtering
- Patch support
- Global Exception Handling
- Clean layered architecture
