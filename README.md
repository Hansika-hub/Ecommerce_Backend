# E-Commerce Backend

A Java-based backend application for an e-commerce platform, built with
Spring Boot. It uses MongoDB for data persistence and Spring Security
with JWT support for authentication and application security.

## Technology Stack

-   **Java 17**
-   **Spring Boot 3.2.5**
-   **Spring Web** -- RESTful web application support
-   **Spring Data MongoDB** -- MongoDB integration
-   **Spring Security** -- Application security
-   **JSON Web Token (JWT)** -- Token-based authentication support
-   **Spring Boot Validation** -- Request/data validation
-   **Lombok** -- Boilerplate code reduction
-   **Maven** -- Dependency management and build

## Prerequisites

-   JDK 17
-   MongoDB (local instance or hosted cluster)
-   Git

## Getting Started

### 1. Clone the repository

``` bash
git clone https://github.com/Hansika-hub/Ecommerce_Backend.git
cd Ecommerce_Backend
```

### 2. Configure MongoDB and application settings

Review the application's configuration under `src/main/resources` and
set the MongoDB connection details and any required security/JWT
settings for your environment.

Do not commit database credentials, JWT secrets, or other sensitive
configuration values.

### 3. Run the application

On macOS/Linux:

``` bash
./mvnw spring-boot:run
```

On Windows:

``` bash
mvnw.cmd spring-boot:run
```

Alternatively, build the project:

``` bash
./mvnw clean package
```

## Project Structure

``` text
Ecommerce_Backend/
├── .mvn/
│   └── wrapper/
├── src/
│   └── main/
│       ├── java/          # Application source code
│       └── resources/     # Application configuration
├── mvnw                  # Maven wrapper (Unix)
├── mvnw.cmd              # Maven wrapper (Windows)
└── pom.xml               # Maven project configuration
```

## Related Repository

-   **Frontend:** https://github.com/Hansika-hub/Ecommerce_Frontend

## Notes

-   The project targets Java 17 and Spring Boot 3.2.5.
-   A reachable MongoDB instance is required for database-backed
    functionality.
-   Configure environment-specific settings before running the
    application.
-   Keep secrets and credentials out of version control.

------------------------------------------------------------------------
