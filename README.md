# db-sync

A modern Java Spring Boot application for synchronizing user data across PostgreSQL and Neo4j databases. Built with Docker, Maven, and Java 21 for seamless local development and deployment.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 🚀 Features

- RESTful API with Spring Boot
- Dual database support: PostgreSQL (relational) & Neo4j (graph)
- Dockerized for fast local setup
- Automatic schema management with Hibernate
- Easy configuration via environment variables

---

## 📦 Prerequisites

- [Java 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- [Docker & Docker Compose](https://docs.docker.com/compose/)

---

## 🏁 Getting Started

Clone the repository and start all services with Docker Compose:

```bash
git clone https://github.com/feelipino/db-sync.git
cd db-sync
docker-compose up --build
```

- API: [http://localhost:8080](http://localhost:8080)
- PostgreSQL: `localhost:5432` (user: `postgres`, password: `1234`)
- Neo4j: [http://localhost:7474](http://localhost:7474) (user: `neo4j`, password: `abcd1234`)

---

## ⚙️ Configuration

Main environment variables (see `docker-compose.yml`):

- `SPRING_DATASOURCE_URL` — PostgreSQL JDBC URL
- `SPRING_NEO4J_URI` — Neo4j Bolt URI

You can also adjust settings in `src/main/resources/application.properties`.

---

## 🗂️ Project Structure

- `src/main/java` — Application source code
- `src/main/resources` — Configuration files
- `Dockerfile` — Multi-stage build for the app
- `docker-compose.yml` — Service orchestration
- `pom.xml` — Maven dependencies and build config

---

## 🧪 API Usage (CRUD Examples)

Replace `{id}` with the actual user ID.

### Create User

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'
```

### Read All Users

```bash
curl http://localhost:8080/users
```

### Read User by ID

```bash
curl http://localhost:8080/users/{id}
```

### Update User

```bash
curl -X PUT http://localhost:8080/users/{id} \
  -H "Content-Type: application/json" \
  -d '{"username":"alice_updated","password":"newpass"}'
```

### Delete User

```bash
curl -X DELETE http://localhost:8080/users/{id}
```

---

## 🛠️ Development

Build and run locally (without Docker):

```bash
mvn clean package
java -jar target/*.jar
```

---

## 🤝 Contributing

Contributions are welcome! Please open issues or submit pull requests.

---

## 📄 License

This project is licensed under the MIT License.

---
