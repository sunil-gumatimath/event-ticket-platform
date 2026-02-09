# Tickets

Event ticket platform for booking and managing events.

## Tech Stack

- Java 21
- Spring Boot 3.2.4
- Spring Security with OAuth2
- Spring Data JPA
- H2 (dev) / PostgreSQL (prod)
- Maven

## Domain Model

The core entities of the application include:

- **User**: Represents organizers, attendees, and staff.
- **Event**: Core entity representing an event with details like venue, schedule, and status.
- **TicketType**: Defines different categories of tickets (e.g., VIP, General) for an event.
- **Ticket**: An individual ticket issued to a user.
- **QrCode**: Security feature associated with a ticket for validation.
- **TicketValidation**: Records validation attempts (e.g., scanning at entry).

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

---

*Documentation in progress...*
