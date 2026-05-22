# BloomChain – Flower Shop Microservices Platform

BloomChain is a full-stack flower shop management platform built using a microservices architecture.  
The project demonstrates the use of Domain-Driven Design (DDD), Spring Boot microservices, React frontend development, REST APIs, Docker containerization, and distributed system principles.

---

# Features

The platform supports multiple user roles and functionalities.

## Client
- View flowers and available products
- Search flowers by name
- View flower details and images
- Change application language

## Employee
- Manage flower stock
- Update quantities and flower information
- Process flower sales
- Export flower lists

## Manager
- CRUD operations for flowers
- Generate statistics and reports
- Manage inventory across flower shops
- Filter flowers and stock data

## Administrator
- Manage users and roles
- Send notifications
- Perform administrative operations

---

# Architecture

The application follows a microservices architecture where each service is independent and owns its own database.

## Microservices
- FlowerCatalogService
- InventoryService
- UserManagementService
- StatisticsService
- NotificationService
- ApiGatewayService

## Frontend
- React
- REST API communication
- Multi-language support

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- REST APIs

## Databases
Each microservice uses its own MySQL database:
- flower_catalog_db
- inventory_db
- user_management_db
- statistics_service_db
- notification_db

---

# Technologies Used

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- MySQL

## Frontend
- React
- JavaScript
- CSS

## DevOps / Infrastructure
- Docker
- Docker Compose
- Nginx

## Tools
- Git
- GitHub
- Postman

## Notifications
- Gmail SMTP
- Discord Webhook API

---

# Design Patterns

The project uses several software design patterns:
- Singleton Pattern
- Facade Pattern
- Strategy Pattern
- Observer Pattern
- DTO Pattern

---

# Docker Support

The entire application can be started using Docker Compose.

## Start the application

```bash
docker compose up --build
```

## Stop containers

```bash
docker compose down
```

## Remove containers and database volumes

```bash
docker compose down -v
```

---

# Docker Architecture

The Docker setup includes:
- one container for each microservice
- one MySQL container
- one Nginx container for the frontend
- internal Docker networking
- persistent database volumes
- environment-based configuration

---

# Ports

| Service | Port |
|---|---|
| Frontend | 5173 |
| API Gateway | 8080 |
| FlowerCatalogService | 8081 |
| InventoryService | 8082 |
| UserManagementService | 8083 |
| StatisticsService | 8084 |
| NotificationService | 8085 |
| MySQL | 3306 |

---

# Running the Application

After starting the containers:

Frontend:
```txt
http://localhost:5173
```

API Gateway:
```txt
http://localhost:8080
```

---

# API Communication

The frontend communicates with backend services through the API Gateway using REST APIs.

Microservices communicate between each other using HTTP REST requests.

Examples:
- InventoryService → FlowerCatalogService
- UserManagementService → NotificationService
- InventoryService → StatisticsService

---

# Notifications

The application supports:
- Email notifications using Gmail SMTP
- Discord notifications using Webhooks

---

# Statistics

The StatisticsService generates:
- total sales
- total profit
- shop statistics
- best-selling flowers
- revenue reports

---

# Export Support

The application supports exporting data in:
- CSV
- JSON
- XML
- DOC

---

# Project Structure

```txt
BloomChain/
│
├── ApiGatewayService/
├── FlowerCatalogService/
├── InventoryService/
├── UserManagementService/
├── StatisticsService/
├── NotificationService/
├── FlowerShopClient/
│
├── docker-compose.yml
├── DOCKER.md
└── README.md
```

---

# Future Improvements

Possible future improvements:
- JWT authentication
- Kubernetes deployment
- CI/CD pipelines
- Cloud deployment
- Caching support
- Message brokers (Kafka/RabbitMQ)
- Monitoring and logging
- Improved recommendation system

---

# Educational Purpose

This project was developed to demonstrate:
- microservices architecture
- DDD principles
- REST API communication
- frontend-backend integration
- Docker containerization
- layered architecture
- software design patterns
- distributed systems concepts

---

Developed as part of a university software engineering project.
