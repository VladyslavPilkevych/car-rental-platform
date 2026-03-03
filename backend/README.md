# Car Rental Platform - Backend

This is a Spring Boot application that provides an API for car availability and reservation management.

## Getting Started

To run the application, use the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The server will start on [http://localhost:8080](http://localhost:8080).

## API Endpoints

- **`GET /api/availability`**
  - **Query Parameters:**
    - `from` (optional): Start date in `YYYY-MM-DD` format.
    - `to` (optional): End date in `YYYY-MM-DD` format.
  - **Description:** Returns a list of cars with their availability status, estimated price for the period, and any conflicting reservations.

## Project Structure

```text
src/main/java/com/example/backend/
├── controller/     # REST Controllers (API endpoints)
├── dto/            # Data Transfer Objects (API request/response)
├── model/          # Domain models (Car, Reservation)
├── service/        # Business logic (Availability calculation)
└── exception/      # Global exception handling
```

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.0**
- **Maven**
- **JUnit 5 / Mockito** (for testing)

## Testing

To run the tests:

```bash
./mvnw test
```

## Running with Docker

You can also run the application using Docker:

1. **Build the image:**

   ```bash
   docker build -t car-rental-backend .
   ```

2. **Run the container:**
   ```bash
   docker run -p 8080:8080 car-rental-backend
   ```

The API will be available at [http://localhost:8080](http://localhost:8080).
