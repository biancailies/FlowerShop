# Docker setup

This project is containerized with one container per microservice, one React client container, and one shared MySQL container.

## Run everything

```powershell
docker compose up --build
```

Open the application at:

```text
http://localhost:5173
```

The API gateway is exposed at:

```text
http://localhost:8080
```

## Services

- `flower-shop-client`: React app served by Nginx on host port `5173`
- `api-gateway-service`: Spring Cloud Gateway on host port `8080`
- `flower-catalog-service`: host port `8081`
- `inventory-service`: host port `8082`
- `user-management-service`: host port `8083`
- `statistics-service`: host port `8084`
- `notification-service`: host port `8085`
- `mysql`: MySQL 8.4 on host port `3306`

## Optional notification credentials

Copy `.env.example` to `.env` if you want to test real email or Discord notifications:

```powershell
Copy-Item .env.example .env
```

Then fill in the values in `.env`. The application can run without these values, but real outgoing notifications need credentials.

## Reset the database

The MySQL data is stored in the `mysql-data` Docker volume. To recreate the database from `docker/mysql/init/01-init-databases.sql`:

```powershell
docker compose down -v
docker compose up --build
```

## Stop containers

```powershell
docker compose down
```
