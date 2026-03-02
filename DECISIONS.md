# DECISIONS.md

## Data issues I found

- Missing or blank identifiers
  - `carId` can be missing/blank for cars or reservations
- Missing or blank car fields
  - `model` can be empty or missing
- Invalid price values
  - `pricePerDay` can be missing, zero or negative
- Invalid reservation dates
  - missing `from` or `to`
  - `from` >= `to`
  - invalid date format
- Unclear reservation statuses
  - unknown values or missing `status`
  - cancelled reservations that should be ignored

## How I handled the data

### Backend structure

I tried to keep backend simple and predictable:

- Load `cars.json` and `reservations.json` from `resources/data` at startup using Jackson.
- Build an in-memory index `reservationsByCarId` to avoid scanning all reservations on each request.
- Separate responsibilities:
  - `AvailabilityService` handles automatization and aggregation.
  - `ReservationClassifier` encapsulates reservation rules.

This keeps parsing, classification, and availability calculation clearly separated.

### Validation and API errors

The endpoint validates:

- required `from` and `to`
- correct `YYYY-MM-DD` format
- `from < to`
- computed `days > 0`

Invalid input returns `400` with a consistent JSON body:

```json
{ "message": "..." }
```

Unexpected errors return `500` with a generic message via a global
exception handler.

### Reservation classification

Each reservation is classified into:

- **IGNORED** - `CANCELLED`, no effect.
- **DATA_CONFLICT** - missing/invalid dates or `to <= from`
  - increases `conflicts`
  - never blocks availability
- **NO_OVERLAP** - valid but outside interval.
- **HARD_OVERLAP** - overlap + `CONFIRMED` or `PICKED_UP`
  - blocks availability
  - counts as conflict
- **SOFT_OVERLAP** - overlap + unknown/missing status
  - counts as conflict
  - does not block availability

Overlap is handled as half-open intervals `[from, to)`:

This avoids boundary bugs and is covered by a dedicated test.

### Price calculation

`estimatedPrice` is calculated only when:

- the car is available
- `pricePerDay > 0`

Otherwise it is `null`, since price is only meaningful for bookable cars.

### Optimization

- Reservations are grouped by `carId` once during initialization.
- Availability checks operate only on relevant reservations.
- Classification logic is centralized to avoid duplication.
- Unit tests run without starting the full Spring context, keeping feedback fast.

## Tests

Unit tests focus on:

- boundary overlap behavior
- mixed scenarios with HARD / SOFT / DATA / CANCELLED cases and correct `isAvailable`, `conflicts`, and `estimatedPrice`

Tests are isolated and fast.

## Frontend decisions

- API logic is centralized and parses `{ message }` error responses.
- Results are sorted: available cars first, then alphabetically by model.
- A reusable table component avoids duplicated UI logic.
- Skeleton loaders improve perceived performance.
- Errors are shown explicitly with user-friendly messages.
- A pre-commit hook runs linting and tests before commits.

## What I would improve in production

- Add caching for frequent `(from, to)` queries.
- Provide OpenAPI documentation and stronger contract testing.
- Add controller-level tests and broader edge-case coverage.
