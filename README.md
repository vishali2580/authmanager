# AuthManager

A Spring Boot REST API for user registration and authentication, using PostgreSQL for data storage, BCrypt for password hashing, and JWT for stateless authentication.

## Features

- User registration with hashed password storage (BCrypt)
- Login endpoint that verifies credentials and returns a signed JWT
- Stateless authentication — no server-side sessions
- Public auth endpoints configured via Spring Security
- PostgreSQL persistence via Spring Data JPA (Hibernate auto-generates schema)

## Tech Stack

- Java 17
- Spring Boot 4.1
- Spring Data JPA / Hibernate
- Spring Security (BCryptPasswordEncoder)
- PostgreSQL
- JWT (jjwt library)
- Maven

## API Endpoints

### Register a new user
```
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword",
  "fullName": "Your Name"
}
```

### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword"
}
```
Returns a JWT token on success:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login successful"
}
```

## How It Works

- Passwords are hashed with BCrypt before being stored — plain text passwords are never saved.
- On successful login, the server generates a JWT signed with a secret key, containing the user's email and an expiration time (1 hour).
- Spring Security is configured to allow public access to `/api/auth/**` while protecting all other routes by default.

## Running Locally

1. Create a PostgreSQL database named `authmanager_db`
2. Update `src/main/resources/application.properties` with your database credentials
3. Run:
```bash
mvn spring-boot:run
```
4. The API will be available at `http://localhost:8080`

## Learning Notes

This project was built to understand core backend concepts hands-on: REST API design, ORM with JPA/Hibernate, password security, dependency injection, and stateless JWT authentication.
