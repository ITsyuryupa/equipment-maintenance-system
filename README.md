# Запуск приложения через Docker

## Сборка проектов

Перед запуском необходимо собрать оба сервиса.

EquipmentService:

```bash
cd EquipmentService
mvn clean package -DskipTests
```

MaintenanceService:

```bash
cd MaintenanceService
mvn clean package -DskipTests
```

После сборки в папках `target` будут созданы:

- equipment-service.jar
- maintenance-service.jar

## Запуск

Из корневой директории проекта выполнить:

```bash
docker compose up --build
```

При первом запуске Docker скачает необходимые образы и соберет контейнеры.

## Остановка

```bash
docker compose down
```

## Состав системы

После запуска будут доступны:

| Сервис | Адрес |
|--------|--------|
| Equipment Service | http://localhost:8081 |
| Maintenance Service | http://localhost:8082 |
| Equipment Swagger | http://localhost:8081/swagger-ui/index.html |
| Maintenance Swagger | http://localhost:8082/swagger-ui/index.html |

## Базы данных

### Equipment Database

```
Host: localhost
Port: 5432
Database: equipment_db
User: postgres
Password: postgres
```

### Maintenance Database

```
Host: localhost
Port: 5433
Database: maintenance_db
User: postgres
Password: postgres
```

## Контейнеры

После запуска будут созданы четыре контейнера:

- equipment-db
- maintenance-db
- equipment-service
- maintenance-service
