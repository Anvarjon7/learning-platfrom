# Khwarizmi — E-Learning Platform Backend

**AI Academy** is a modular e-learning backend system that powers a scalable platform for courses, 
user management, and content delivery.  
It is built with modern backend best practices, ready for content uploads, course/lesson management, 
and secure, role-based access.

## 🔧 Tech Stack

- **Java 17** + **Spring Boot**
- **Spring Security** + JWT for authentication & role-based authorization
- **PostgreSQL** for persistent relational data (users, courses, roles, metadata)
- **Spring Data JPA** / Hibernate for data access
- **REST APIs** (controllers / services / repositories)
- **Swagger / OpenAPI** for API documentation (via Springdoc)
- **Docker** (in progress) — for containerized development / future deployment
- **CI/CD** via GitHub + automation tools

## ✅ What works now (MVP core)

- Secure user registration and login (roles: STUDENT, TUTOR, COMPANY, ADMIN)
- JWT-based authentication and role validation
- CRUD endpoints for courses (create/read/update/delete) — restricted to tutors/admins
- Swagger UI for API documentation and testing
- Basic project structure following clean architecture (controller → service → repository)

## 🚀 Continuous Integration & Deployment (CI/CD)

I have integrated a CI/CD pipeline to improve development quality, reliability, and speed of delivery.

### 🔄 How it works

- On every commit or Pull Request → automated build & test suite runs
- On merge to main branch → automatic packaging / artifacts ready for deployment
- Using **GHAWatch**, a lightweight CLI-based monitor for GitHub Actions workflows, we log real-time workflow and job status to console — helpful for developers and maintainers to track CI events.

> **Note:** You can run the monitor with:
> ```bash
> export GITHUB_TOKEN=your_token
> java -jar gha-watch.jar your-repo-owner/your-repo --interval 5 --verbose
> ```
> This will stream events (workflow/run start, job start/finish, step status) in real time.

## 🧪 API Documentation & Testing

After starting the application, the API docs are available at:
http://localhost:8080/swagger-ui/index.html



