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


---

##  Architecture & Security Flow

```text
[ Client / Postman ] 
       │
       ▼
[ JWT Filter ] ──(Extract & Validate Token)──► [ Security Context ]
       │                                               │
       ▼                                               ▼
[ Request Matchers / RBAC ] ───────────► [ Controller Layer ]
 (/schedule/** -> DOCTOR)                             │
 (/patient/**  -> PATIENT)                             ▼
                                           [ Service Layer ]
                                               │        │
                                      (Cache)  ▼        ▼ (DB)
                                          [ Redis ]  [ PostgreSQL ]
```

---

##  Getting Started

### Prerequisites
* **Java Development Kit (JDK):** 17 or higher
* **PostgreSQL:** Running locally on port `5432`
* **Redis:** Running locally on port `6379` (via WSL2, Docker, or native executable)
* **Maven:** 3.8+

###  Installation & Setup

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/hala20110/ClinicApp.git](https://github.com/hala20110/ClinicApp.git)
   cd clinicapp
   ```

2. **Configure Database & Redis (`src/main/resources/application.properties`):**
   ```properties
   server.port=8081

   # PostgreSQL Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/ClinicApp
   spring.datasource.username=postgres
   spring.datasource.password=your_postgres_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # Redis Configuration
   spring.data.redis.host=localhost
   spring.data.redis.port=6379

   # Security JWT Secret
   jwt.secret=c3VwZXItc2VjcmV0LWtleS1mb3ItY2xpbmljLWFwcC1qd3QtYXV0aC1zZXJ2aWNl
   ```

3. **Build and Run the Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

##  API Reference

###  Authentication (`/acc`)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/acc/register` | Register a new user (`DOCTOR` / `PATIENT`) | Public |
| `POST` | `/acc/login` | Authenticate user and receive JWT token | Public |

###  Doctor Schedule (`/schedule`)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/schedule` | Create a new doctor schedule | `DOCTOR` |
| `GET` | `/schedule` | Retrieve all doctor schedules | `DOCTOR` |
| `GET` | `/schedule/doctor/{id}` | Get schedule details by schedule ID | `DOCTOR` |
| `PUT` | `/schedule/{id}` | Update doctor schedule details | `DOCTOR` |

###  Patient Management (`/patient`)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/patient/profile` | Retrieve authenticated patient details | `PATIENT` |
| `PUT` | `/patient/profile` | Update patient medical record details | `PATIENT` |

---

##  Internship Project Highlights

During this Spring Boot Internship at Netpoint, the following complex engineering challenges were solved:
* Designed and executed a custom stateless **Spring Security 6** architecture eliminating session management overhead.
* Resolved PostgreSQL sequence out-of-sync exceptions (`DataIntegrityViolationException`) by creating custom sequence triggers.
* Implemented optimized caching patterns using **Redis**, ensuring low-latency retrieval for schedule listings and proper invalidation upon mutations.
