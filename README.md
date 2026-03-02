# Car Rental Availability Platform

This is a fullstack application built with **Java (Spring Boot)** and **Next.js**, designed to compute and display car availability and pricing for a car rental platform.

The application reads initial data from the `data/` directory (simulating a database with potentially dirty data) and properly handles edge cases, conflicts, and overlaps, according to the specified business rules.

## Project Structure

- `backend/`: Java 21 Spring Boot application serving the REST API.
- `frontend/`: Next.js (App Router) application with React and Chakra UI showing the availability form and results.
- `data/`: JSON files acting as the data source (`cars.json`, `reservations.json`) and specific test scenarios (`scenarios_public.json`).

---

## Prerequisites

Make sure you have the following installed on your machine:

- **Java 21** or higher
- **Node.js** (v18+ recommended)
- **npm** (comes with Node.js)

---

## How to Run the Application

The project consists of two separate services that need to be run concurrently.

### 1. Start the Backend (Spring Boot)

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Run the application using the Maven Wrapper (no local Maven installation required):
   - **Mac/Linux:**
     ```bash
     ./mvnw spring-boot:run
     ```
   - **Windows:**
     ```cmd
     mvnw.cmd spring-boot:run
     ```
3. The backend API will start on **`http://localhost:8080`**.

### 2. Start the Frontend (Next.js)

1. Open a new terminal window and navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install the required Node.js dependencies:
   ```bash
   npm install
   ```
3. Start the Next.js development server:
   ```bash
   npm run dev
   ```
4. The frontend will start on **`http://localhost:3000`**.

---

## How to Test

### Backend Automated Tests

The backend includes automated tests (for things like date overlap logic, conflict resolution, and data validation).

To run the backend tests:

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Execute the tests via Maven:
   - **Mac/Linux:**
     ```bash
     ./mvnw test
     ```
   - **Windows:**
     ```cmd
     mvnw.cmd test
     ```
