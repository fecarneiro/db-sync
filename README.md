# Java Polyglot Persistence API

A Spring Boot 3 and Java 21 REST API that powerfully demonstrates polyglot persistence, seamlessly synchronizing user data between relational (PostgreSQL) and graph (Neo4j) databases. This project tackles complex data management and relationship handling (e.g., friendships in Neo4j), featuring independent transaction configurations for each database and an optimized local development environment powered by Docker and Docker Compose.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)
![Maven](https://img.shields.io/badge/Maven-managed-red)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 🚀 Features

-   RESTful API with Spring Boot.
-   Dual database support: PostgreSQL (relational) and Neo4j (graph).
-   Friendship relationship management (Neo4j).
-   Dockerized for fast local setup.
-   Automatic schema management with Hibernate for PostgreSQL.
-   Easy configuration via `application.properties` or environment variables.
-   Separate transaction management for PostgreSQL and Neo4j.

---

## 🛠️ Technologies Used

-   **Java 21**
-   **Spring Boot 3.4.4**
    -   Spring Web
    -   Spring Data JPA
    -   Spring Data Neo4j
-   **PostgreSQL**: Relational database.
-   **Neo4j**: Graph database.
-   **Maven**: Dependency management and build tool.
-   **Docker & Docker Compose**: Containerization and service orchestration.
-   **Lombok**: Boilerplate code reduction.

---

## 📦 Prerequisites

-   [Java 21+](https://adoptium.net/)
-   [Maven 3.9+](https://maven.apache.org/)
-   [Docker & Docker Compose](https://docs.docker.com/compose/)

---

## 🏁 Getting Started

Clone the repository and start all services with Docker Compose:

```bash
git clone https://github.com/feelipino/db-sync.git
cd db-sync
docker-compose up --build
```

-   API: [http://localhost:8080](http://localhost:8080)
-   PostgreSQL: `localhost:5432` (user: `postgres`, password: `1234`)
-   Neo4j Browser: [http://localhost:7474](http://localhost:7474) (user: `neo4j`, password: `abcd1234`)
-   Neo4j Bolt: `bolt://localhost:7687`

---

## ⚙️ Configuration

The main application settings can be found in `src/main/resources/application.properties`.

Environment variables (see `docker-compose.yml` for examples):

-   `SPRING_DATASOURCE_URL`: PostgreSQL JDBC URL.
-   `SPRING_DATASOURCE_USERNAME`: PostgreSQL username.
-   `SPRING_DATASOURCE_PASSWORD`: PostgreSQL password.
-   `SPRING_NEO4J_URI`: Neo4j Bolt URI.
-   `SPRING_NEO4J_AUTHENTICATION_USERNAME`: Neo4j username.
-   `SPRING_NEO4J_AUTHENTICATION_PASSWORD`: Neo4j password.

The application uses separate transaction manager configurations for PostgreSQL (`PostgresConfig.java`) and Neo4j (`Neo4jConfig.java`), allowing atomic operations in each database independently.

---

## 🗂️ Project Structure

-   `src/main/java/com/example/dbsync`: Application source code.
    -   `config`: Configuration classes for Beans, JPA, Neo4j, and transaction managers.
    -   `user`: Entities (PostgreSQL and Neo4j), repositories, services, and controllers related to users.
    -   `friendship`: Models, services, and controllers for managing friendship relationships in Neo4j.
-   `src/main/resources`: Configuration files, including `application.properties`.
-   `Dockerfile`: Defines the multi-stage Docker image for the application.
-   `docker-compose.yml`: Orchestrates the application services (app, PostgreSQL, Neo4j).
-   `pom.xml`: Maven configuration, dependencies, and build plugins.

---

## 🧪 API Usage

The base prefix for all API endpoints is `/api`.

### Users (`/users`)

Replace `{id}` with the actual user ID.

**Create User**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'
```

**List All Users**

```bash
curl http://localhost:8080/api/users
```

**Get User by ID**

```bash
curl http://localhost:8080/api/users/{id}
```

**Update User**

```bash
curl -X PUT http://localhost:8080/api/users/{id} \
  -H "Content-Type: application/json" \
  -d '{"username":"alice_updated","password":"newpass"}'
```

**Delete User**

```bash
curl -X DELETE http://localhost:8080/api/users/{id}
```

### Friendships (`/friendships`)

**Request Friendship**

```bash
curl -X POST "http://localhost:8080/api/friendships/request?sourceUserId=1&targetUserId=2"
```

**Accept Friendship**

```bash
curl -X POST "http://localhost:8080/api/friendships/accept?sourceUserId=1&targetUserId=2"
```

**Reject Friendship**

```bash
curl -X POST "http://localhost:8080/api/friendships/reject?sourceUserId=1&targetUserId=2"
```

---

## 🛠️ Development (Local, without Docker)

To build and run the application locally without Docker:

1.  Ensure PostgreSQL and Neo4j instances are running and accessible as configured in `application.properties`.
2.  Compile and package the application:
    ```bash
    mvn clean package
    ```
3.  Run the generated JAR:
    ```bash
    java -jar target/db-sync-0.0.1-SNAPSHOT.jar
    ```

---

## 🤝 Contributing

Contributions are welcome! Please open issues or submit pull requests.

---

## 📄 License

This project is licensed under the MIT License.
```