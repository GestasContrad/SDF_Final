# Learning Management System (LMS) API

## Project Overview
This is a Spring Boot-based RESTful API for a Learning Management System. The application allows educational institutions to manage the core learning flow. The system automates interactions between participants by providing endpoints to manage **users, courses, lessons, assignments, and submissions**. It features a robust security layer with JWT-based authentication and Role-Based Access Control (RBAC).

## Tech Stack
* **Backend:** Java 21, Spring Boot 3.4.2
* **Database & Migrations:** PostgreSQL, Liquibase
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **API Documentation:** Swagger (OpenAPI 3.0)
* **Testing & QA:** JUnit 5, Mockito, Jacoco (Code Coverage)
* **Build Tool:** Gradle
* **Infrastructure:** Docker, Docker Compose

## Roles & Access Control
The system utilizes strict RBAC with three primary roles:
* **STUDENT:** Can view enrolled courses, access lesson materials, and upload submissions for assignments.
* **TEACHER:** Can create and manage courses, add lessons, create assignments, and evaluate/grade student submissions.
* **ADMIN:** Has full access to manage system users, roles, and global platform configurations.

## Architecture

    src/main/java/com/example/sdf_final/
    ├── config/             # Security, Swagger, and Beans settings
    ├── controller/         # REST API Endpoints
    ├── dto/                # Request and Response objects (MapStruct)
    ├── entity/             # Database Entities (User, Course, Lesson...)
    ├── mapper/             # MapStruct Mappers
    ├── repository/         # Spring Data JPA Interfaces
    ├── security/           # JWT Filters and Auth Services
    └── service/            # Business Logic (Interfaces and Impl)

The project follows a classic layered architecture to ensure separation of concerns and maintainability:
* **Controller Layer:** Handles incoming HTTP requests, validates input, and returns DTOs.
* **Service Layer:** Contains all business logic, including validation rules, grading boundaries, and relationship management.
* **Repository Layer:** Interacts with the database using Spring Data JPA.
* **DTO & Mapping:** MapStruct is utilized for high-performance, type-safe mapping between internal Entities and external DTOs.

## Environment Variables
Before running the application, ensure the following environment variables are configured (via your IDE, `.env` file, or `application.properties`):
* `SPRING_DATASOURCE_URL` (default: `jdbc:postgresql://localhost:5432/lms_db`)
* `SPRING_DATASOURCE_USERNAME` (default: `postgres`)
* `SPRING_DATASOURCE_PASSWORD` (default: `postgres`)
* `JWT_SECRET` (A strong, base64-encoded secret key used for signing tokens)

## Database
* **Engine:** PostgreSQL running on port `5432`.
* **Database Name:** `lms_db` (or as configured in your environment).
* **Migrations:** The database schema is strictly versioned using **Liquibase**. Migration files and initial seed data scripts are located in `src/main/resources/db/changelog/`. The schema is automatically applied on application startup to prevent data loss.

## How to Run

1. **Clone the repository:**
   git clone <repository-url>
   cd lms-api

2. **Start the database via Docker:**
   docker compose up -d

3. **Run the application:**
   ./gradlew bootRun

## API Documentation & Examples

### Swagger UI
Once the application is running, the interactive OpenAPI specification (Swagger) is available at:
**http://localhost:8080/swagger-ui/index.html**

### API Examples

**1. Register a new user:**
`POST /api/auth/register`

    {
      "email": "student@test.com",
      "password": "password123",
      "role": "STUDENT"
    }

**2. Login:**
`POST /api/auth/login`

    {
      "email": "student@test.com",
      "password": "password123"
    }
*(Returns a JSON response containing the JWT Token)*

**3. Authenticated Requests:**
For any protected endpoints, include the JWT token in the HTTP Authorization header:
`Authorization: Bearer <your_jwt_token>`

*(Note: The project also includes a complete Postman Collection in the `/postman` directory for automated API testing and token storage).*

## Testing & Coverage
The project includes a comprehensive verification suite, fulfilling the requirements for isolated unit tests and integration tests via Testcontainers.

* **Run all tests:**
  ./gradlew test

* **Generate Code Coverage Report:**
  ./gradlew jacocoTestReport

* **View Coverage:**
  After running the Jacoco task, open the generated HTML report in your browser:
`build/reports/jacoco/test/html/index.html`