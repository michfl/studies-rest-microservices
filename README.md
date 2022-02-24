# Studies REST and microservices project

## About:
- Simple microservices storing data for delivery methods, orders and invoice files in `.pdf` format.
- Additionally, a simple website has been created for viewing and managing uploaded data.

## Used technologies:
- Java Spring
- Docker and Docker Compose
- NGINX Reverse Proxy and Load Balancer

## Running:
- Use `docker-compose up` command in `project` directory.
- Wait around 1 min for all services to start.

## Before running docker compose:
- Make sure that `project/docker-compose.yml` file has proper absolute file path set to the volumes sources. In this folder uploaded files will be stored.

## If some services did not manage to start:
- Try tweaking delays in `entrypoint.sh` files localized in `microservices/*` directory.
- Proper startup requires that orders service will start before deliveries service.
