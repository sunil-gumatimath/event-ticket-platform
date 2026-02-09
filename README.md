# Tickets

Event ticket platform for booking and managing events.

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.4**
- **Spring Security** (OAuth2 Resource Server)
- **Spring Data JPA**
- **Database**: H2 (Development) / PostgreSQL (Production)
- **Tools**: Maven, Lombok, MapStruct

## Features

- **User Management**: Organizers, Attendees, and Staff.
- **Event Management**: Create and manage events with schedules and venues.
- **Ticketing**: 
    - Multiple ticket types (VIP, General, etc.)
    - Ticket purchasing and issuance.
    - QR Code generation for tickets.
- **Validation**: 
    - Secure ticket validation via QR Code scanning.
    - Tracking of validation attempts and status.

## Domain Model

The core entities of the application include:

- **User**: Represents system users including event organizers, attendees, and staff members.
- **Event**: Core entity representing an event with details like venue, schedule, sales period, and status.
- **TicketType**: Defines different categories of tickets (e.g., VIP, General) for a specific event with pricing and availability.
- **Ticket**: An individual ticket issued to a user for a specific event.
- **QrCode**: Security feature associated with a ticket, containing a unique value for validation.
- **TicketValidation**: Records validation attempts (e.g., scanning at entry), capturing status and method.

## Getting Started

### Prerequisites

- Java 21 SDK
- Maven

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

By default, the application runs on port `8080`.
