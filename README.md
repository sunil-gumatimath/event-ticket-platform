# Event Ticket Platform

Event ticket platform for booking and managing events with secure QR code validation, built on Spring Boot and secured with Keycloak OAuth2.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.2.4 |
| Security | Spring Security, OAuth2 Resource Server (JWT), Keycloak |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL 16 (Docker) |
| Mapping | MapStruct 1.6.3 |
| Validation | Jakarta Bean Validation (spring-boot-starter-validation) |
| Build | Maven, Lombok 1.18.36 |
| Infrastructure | Docker Compose (PostgreSQL, Keycloak, Adminer) |

## Features

- **User Management**: Auto-provisioning of users from Keycloak JWT tokens. Supports Organizers, Attendees, and Staff roles.
- **Event Management**: Create and manage events with schedules, venues, sales periods, and lifecycle statuses (Draft, Published, Cancelled, Completed).
- **Ticketing**:
    - Multiple ticket types per event (VIP, General, etc.) with pricing and availability.
    - Ticket purchasing and issuance.
    - QR Code generation for ticket entry.
- **Validation**:
    - Secure ticket validation via QR Code scanning or manual check.
    - Tracking of validation attempts, status (Valid, Invalid, Expired), and method.
- **Security**: Stateless JWT authentication via Keycloak. All endpoints require a valid Bearer token.

## Project Structure

```
com.ted.tickets
├── config/                          # Security and JPA configuration
│   ├── SecurityConfig               # OAuth2 resource server, filter chain
│   └── JpaConfiguration             # JPA auditing (@CreatedDate, @LastModifiedDate)
│
├── entity/                          # JPA entities and enums
│   ├── Event                        # Core event entity
│   ├── EventStatusEnum              # DRAFT, PUBLISHED, CANCELLED, COMPLETED
│   ├── Ticket                       # Individual ticket issued to a user
│   ├── TicketStatusEnum             # PURCHASED, CANCELLED
│   ├── TicketType                   # Ticket category with pricing
│   ├── TicketValidation             # Validation attempt record
│   ├── TicketValidationMethod       # QR_SCAN, MANUAL
│   ├── TicketValidationStatusEnum   # VALID, INVALID, EXPIRED
│   ├── QrCode                       # QR code linked to a ticket
│   ├── QrCodeStatusEnum             # ACTIVE, EXPIRED
│   └── User                         # System user (organizer/attendee/staff)
│
├── dto/                             # Data Transfer Objects (API layer)
│   ├── request/                     # Incoming request payloads
│   │   ├── CreateEventRequestDto
│   │   └── CreateTicketTypeRequestDto
│   └── response/                    # Outgoing response payloads
│       ├── CreateEventResponseDto
│       └── CreateTicketTypeResponseDto
│
├── domain/                          # Internal domain models
│   └── model/                       # Service-layer models (no validation)
│       ├── CreateEventRequest
│       └── CreateTicketTypeRequest
│
├── exceptions/                      # Custom exception hierarchy
│   ├── EventTicketException         # Base runtime exception
│   └── UserNotFoundException        # Thrown when user lookup fails
│
├── filters/                         # Servlet filters
│   └── UserProvisioningFilter       # Auto-creates users from Keycloak JWT
│
├── mappers/                         # MapStruct mappers
│   └── EventMapper                  # DTO <-> Entity conversions
│
├── repository/                      # Spring Data JPA repositories
│   ├── EventRepository
│   └── UserRepository
│
├── services/                        # Business logic
│   ├── EventService                 # Service interface
│   └── impl/
│       └── EventServiceImpl         # Service implementation
│
└── TicketsApplication               # Spring Boot entry point
```

## Domain Model

```
User (1) ────< (N) Event                [organizer -> organizedEvents]
User (N) >───< (N) Event                [attendingEvents / attendees]
User (N) >───< (N) Event                [staffingEvents / staff]
Event (1) ────< (N) TicketType
TicketType (1) ────< (N) Ticket
Ticket (N) >──── (1) User               [purchaser]
Ticket (1) ────< (N) QrCode
Ticket (1) ────< (N) TicketValidation
```

### Database Tables

| Table | Description |
|-------|-------------|
| `users` | System users synced from Keycloak |
| `events` | Events with venue, schedule, status |
| `ticket_types` | Ticket categories per event |
| `tickets` | Individual tickets linked to purchasers |
| `qr_code` | QR codes for ticket validation |
| `ticket_validation` | Validation attempt records |
| `user_attending_events` | Join table: users attending events |
| `user_staffing_events` | Join table: users staffing events |

## Getting Started

### Prerequisites

- Java 21 SDK
- Maven
- Docker & Docker Compose

### 1. Start Infrastructure

```bash
docker-compose up -d
```

This starts:
- **PostgreSQL** on port `5432` (password: `root`)
- **Keycloak** on port `9090` (admin/admin)
- **Adminer** (DB UI) on port `8888`

### 2. Configure Keycloak

1. Open `http://localhost:9090` and login with `admin` / `admin`.
2. Create a realm named `event-ticket-platform`.
3. Create a client for the application.
4. Create test users as needed.

### 3. Build and Run

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

The application runs on port `8080` by default.

### Configuration

Key settings in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: changemeinprod!

  jpa:
    hibernate:
      ddl-auto: update

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9090/realms/event-ticket-platform
```

## Authentication

All API endpoints require a valid JWT Bearer token issued by Keycloak. On first authenticated request, the `UserProvisioningFilter` automatically creates a local user record from the JWT claims (`sub`, `preferred_username`, `email`).

```bash
# Example: Call an endpoint with a Bearer token
curl -H "Authorization: Bearer <your-jwt-token>" http://localhost:8080/api/events
```
