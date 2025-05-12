This project consists of multiple services (`email-manager`, `account-manager`, and `user-manager`) that can be deployed using Docker and Docker Compose.

## Prerequisites

1. Install [Docker](https://docs.docker.com/get-docker/).
2. Install [Docker Compose](https://docs.docker.com/compose/install/).
3. Ensure the JAR files for each service are built using Maven:
   ```bash
   cd account-manager
   mvn clean package
   ``` 
   ```bash
   cd email-manager
   mvn clean package
   ```
   ```bash
   cd users-manager
   mvn clean package
   ```
## Running the Services with Docker Compose

1. Navigate to the **infrastructure** directory where the `docker-compose.yaml` file is located:
    ```bash
    cd infrastructure
    ```
2. Start the services using Docker Compose
    ```bash
    docker-compose up --build
    ```
3. Checking the logs all services are running:
   ```bash
    docker-compose logs -f
    ```
## Accessing the Services

- **User Manager:** http://localhost:8090
- **Account Manager:** http://localhost:8091
- **Email Manager:** http://localhost:8092
- **Kafka UI:** http://localhost:8000
