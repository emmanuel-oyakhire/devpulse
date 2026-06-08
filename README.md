# DevPulse API

A production-grade REST API built with Java Spring Boot, PostgreSQL, and Docker. Deployed live on Railway.

**Base URL:** `https://devpulse-production-68fd.up.railway.app`

## Tech Stack
- Java 21 + Spring Boot 3.5
- Spring Security + JWT Authentication
- PostgreSQL + Spring Data JPA
- Docker + Railway (deployment)
- Jsoup (web scraping)

## Features
- User registration and login with JWT tokens
- BCrypt password hashing
- Protected routes with JWT filter
- Link saving with automatic metadata extraction
- Invoice management with full lifecycle tracking
- Data ownership — users only access their own data
- Global exception handling with proper HTTP status codes

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and get JWT token | No |

### Links
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/links` | Save a URL | Yes |
| GET | `/api/links` | Get all your links | Yes |

### Invoices
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/invoices` | Create an invoice | Yes |
| GET | `/api/invoices` | Get all your invoices | Yes |
| PATCH | `/api/invoices/{id}/status` | Update invoice status | Yes |

### User
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/user/me` | Get current user info | Yes |

## Authentication
All protected endpoints require a Bearer token in the Authorization header:

## Running Locally
```bash
# Start the database
docker compose up -d

# Run the application with local profile
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```

## Author
Emmanuel Oyakhire