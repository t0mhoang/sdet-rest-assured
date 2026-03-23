# SDET REST Assured API Testing

A standalone Java API test suite demonstrating REST Assured for SDET roles.

## What this covers
- GET requests with status code and response body assertions
- POST requests with request body and 201 Created validation
- Query parameter filtering
- Response time performance assertions
- Parameterized API tests with @ParameterizedTest
- Allure reporting with Epic, Feature and Story annotations

## Tech stack
- Java 17
- REST Assured 5.4.0
- JUnit 5.10.0
- Allure 2.25.0
- Maven

## How to run
```bash
mvn clean test
mvn allure:serve
```

## API under test
JSONPlaceholder — https://jsonplaceholder.typicode.com
A free public REST API used for testing and prototyping.

## Project structure
```
src/
├── main/java/com/api/
│   └── config/ApiConfig.java       # Base URL and request spec
└── test/java/com/api/
    └── tests/
        ├── GetPostsTest.java        # GET endpoint tests
        └── CreatePostTest.java      # POST endpoint tests
```