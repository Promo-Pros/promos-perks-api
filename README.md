# Promos & Perks API

This project was developed to showcase technical proficiency in Spring Security, utilizing JWT, authentication/authorization, and BCrypt.

## Features

- User registration and login endpoints
- Secure password storage with BCrypt
- JWT-based authentication and authorization
- RESTful API design using Spring Boot

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- BCrypt
- Maven

## Getting Started

1. **Clone the repository:**
   ```
   git clone [https://github.com/your-username/promos-perks-api.git](https://github.com/Promo-Pros/promos-perks-api.git)
   ```

2. **Build the project:**
   ```
   mvn clean install
   ```

3. **Run the application:**
   ```
   mvn spring-boot:run
   ```

4. **API Endpoints:**
   - `POST /user/register` — Register a new user
   - `POST /user/login` — Authenticate a user and receive a JWT
   - `GET /user/{id}` — Retrieve user details (secured)

## License

This project is for portfolio and demonstration purposes.
