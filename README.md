# Wex Transactions API

A Wex test case API for storing purchase transactions and retrieve them with different currencies.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes.

### Prerequisites

For running this project locally, you should install and run the Docker Engine.

```
Docker - (https://www.docker.com/get-started/)
```

### Building and Running

You can run both the application and the database container using Docker Compose.

Just run this command:

```
docker compose up -d
```

## Using the API

The API is running in port 8080.

### Store a Purchase Transaction

#### Request

Parameters (all fields are mandatory):
- description (max size 50)
- transactionDate (format yyyy-mm-dd)
- amount (decimal number greater than zero)

`POST /purchases/`

    curl --location 'http://localhost:8080/purchases' --header 'Content-Type: application/json' --data '{"description": "Dinner","transactionDate": "2023-11-09","amount": 51.23}'

#### Response

    HTTP/1.1 201 Created
    Date: Fri, 10 Nov 2023 18:23:29 GMT
    Status: 201 Created
    Connection: keep-alive
    Keep-Alive: timeout=60
    Content-Type: application/json
    Location: /purchases/1

    {"id": 1,"description": "Dinner","transactionDate": "2023-11-09","amount": 51.23}

## Running the tests

To run the automated tests, you may use the Gradle Wrapper.

```
./gradlew test
```

## Built With

* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) - The web framework used
* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) - Dependency Management
* [PostgreSQL](https://www.postgresql.org/docs/) - Relational Database
* [Docker](https://docs.docker.com/) - Containerization Tool

## Authors

* **Eduardo Corrêa** - [Tavaresdu](https://github.com/tavaresdu)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details