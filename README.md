# Clinic Management Application (ClinicApp)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-blue.svg)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache-red.svg)](https://redis.io/)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)

A robust, enterprise-ready **Clinic Management System** built with Java, Spring Boot 3, Spring Security 6, PostgreSQL, and Redis. The application provides complete Role-Based Access Control (RBAC) for Doctors and Patients, secure JWT authentication, relational JPA entity management, and high-performance Redis caching.

---

##  Key Features

###  1. Authentication & Security (Spring Security 6 + JWT)
* **Stateless Authentication:** Custom JWT implementation using `JWTFilter` and `JWTService`.
* **Role-Based Access Control (RBAC):** Strict separation of permissions between `DOCTOR` and `PATIENT` roles using Spring Security authorization matchers and `@PreAuthorize` annotations.
* **BCrypt Password Hashing:** Secure password encoding with configurable strength.
* **Custom Error Handling:** Standardized `401 Unauthorized` (authentication failure) and `403 Forbidden` (role-based access denial) JSON responses using custom `AuthenticationEntryPoint` and `AccessDeniedHandler`.

###  2. Doctor & Patient Domain Management
* **Entity Separation:** Decoupled security account identities (`User`) from domain profiles (`Doctor`, `Patient`) via clean JPA `OneToOne` relationships.
* **Doctor Schedules:** Full CRUD operations for managing weekly availability, consultation fees, and specialization details.
* **Patient Management:** Management of medical history fields (blood type, nationality, contact details) linked directly to user accounts.

###  3. High Performance Caching (Redis)
* **Spring Cache Integration:** Accelerated query performance using `@Cacheable` and `@CacheEvict` annotations.
* **Cache Invalidation:** Automated cache eviction on schedule creation or updates to prevent stale data reading.

###  4. Relational Database Management (PostgreSQL & JPA/Hibernate)
* **Automated Identity Management:** Custom PostgreSQL sequence alignment (`setval`) handling auto-increment primary keys reliably across entities.
* **Data Integrity:** Strict constraints prohibiting duplicate user registration with unique email indexes.

---

##  Tech Stack & Tools

| Layer | Technology |
| :--- | :--- |
| **Framework** | Spring Boot 3.x, Spring Web |
| **Security** | Spring Security 6.x, JJWT (JSON Web Token), BCrypt |
| **Persistence** | Spring Data JPA, Hibernate ORM |
| **Database** | PostgreSQL 16 |
| **Caching** | Redis (Spring Data Redis) |
| **Utilities** | Lombok, SLF4J / Logback |
| **API Testing** | Postman |
