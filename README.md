# Autoflex

This project includes a React frontend and a Spring Boot backend, both with Dockerfiles and ready for Docker Compose.

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/)

---

## Environment Variables

Before running the frontend, you will need an `.env` at root directory of frontend with following variable `VITE_API_URL`

Copy the example file:

```sh
cp frontend/.env.example frontend/.env
```

> **Note:** The frontend will not work properly without the `.env` file, as Vite requires environment variables to be set.

## 1. Running ONLY the database with Docker and the rest locally

Run only the database in Docker and run backend/frontend locally.

### Steps

1. Start only the database with Docker Compose at root directory:

    ```sh
    docker compose up -d db
    ```

2. Start the backend locally:

    ```sh
    cd backend
    mvn clean install -DskipTests
    mvn spring-boot:run
    ```

3. Start the frontend locally:

    ```sh
    cd frontend
    npm install
    npm run dev
    ```
---

## 2. Running EVERYTHING with Docker Compose

This will start both frontend and backend in containers.

### Steps

1. In the terminal, at the project root, run:

    ```sh
    docker compose up --build
    ```

2. Access:
    - Frontend: [http://localhost:5173](http://localhost:5173)
    - Backend: [http://localhost:8080](http://localhost:8080)

---

## 3. Running Backend Tests

To run the integration tests for the backend:

### Steps

1. Go to the backend directory:

    ```sh
    cd backend
    ```

2. Run the tests with Maven:

    ```sh
    mvn test
    ```

The test results will be displayed in the terminal.
