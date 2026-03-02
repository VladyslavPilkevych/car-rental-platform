package com.example.backend.service;

import com.example.backend.dto.AvailabilityResponseDto;
import com.example.backend.exception.ValidationException;
import com.example.backend.model.Car;
import com.example.backend.model.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);

    private final ObjectMapper objectMapper;
    private List<Car> cars = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private Map<String, List<Reservation>> reservationsByCarId = new HashMap<>();

    public AvailabilityService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            InputStream carsStream = getClass().getClassLoader().getResourceAsStream("data/cars.json");
            if (carsStream == null) {
                log.warn("cars.json not found in classpath");
            } else {
                com.fasterxml.jackson.databind.JsonNode root = objectMapper.readTree(carsStream);
                if (root.has("cars")) {
                    List<Car> loadedCars = objectMapper.readValue(root.get("cars").traverse(), new TypeReference<>() {
                    });
                    setCars(loadedCars);
                }
            }

            InputStream resStream = getClass().getClassLoader().getResourceAsStream("data/reservations.json");
            if (resStream == null) {
                log.warn("reservations.json not found in classpath");
            } else {
                com.fasterxml.jackson.databind.JsonNode root = objectMapper.readTree(resStream);
                if (root.has("reservations")) {
                    List<Reservation> loadedReservations = objectMapper.readValue(root.get("reservations").traverse(),
                            new TypeReference<>() {
                            });
                    setReservations(loadedReservations);
                }
            }
        } catch (Exception e) {
            log.warn("Data init failed - {}", e.getMessage(), e);
        }
    }

    public void setCars(List<Car> cars) {
        this.cars = cars == null ? new ArrayList<>() : cars;
        validateCars(this.cars);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations == null ? new ArrayList<>() : reservations;
        this.reservationsByCarId = this.reservations.stream()
                .filter(res -> {
                    if (res.carId() == null || res.carId().isBlank()) {
                        log.warn("Reservation missing carId ignored: reservationId={}", res.reservationId());
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.groupingBy(Reservation::carId));
    }

    private void validateCars(List<Car> carsToValidate) {
        for (Car car : carsToValidate) {
            String carIdentifier = (car.carId() != null && !car.carId().isBlank()) ? car.carId() : "UNKNOWN_CAR_ID";

            if (car.carId() == null || car.carId().isBlank()) {
                log.warn("Car has missing or blank carId. Model: {}", car.model());
            }
            if (car.model() == null || car.model().isBlank()) {
                log.warn("Car has missing or blank model for carId: {}", carIdentifier);
            }
            if (car.pricePerDay() == null || car.pricePerDay().compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("Invalid pricePerDay for carId={} (Model: {})", carIdentifier, car.model());
            }
        }
    }

    public List<AvailabilityResponseDto> checkAvailability(String fromStr, String toStr) {
        if (fromStr == null || toStr == null || fromStr.isBlank() || toStr.isBlank()) {
            throw new ValidationException("from and to are required");
        }

        LocalDate from;
        LocalDate to;
        try {
            from = LocalDate.parse(fromStr);
            to = LocalDate.parse(toStr);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format, expected YYYY-MM-DD");
        }

        if (!from.isBefore(to)) {
            throw new ValidationException("from must be before to");
        }

        long days = ChronoUnit.DAYS.between(from, to);
        if (days <= 0) {
            throw new ValidationException("days must be > 0");
        }

        return cars.stream().map(car -> {
            boolean isAvailable = true;
            int conflicts = 0;

            if (car.carId() != null && !car.carId().isBlank()) {
                List<Reservation> carReservations = reservationsByCarId.getOrDefault(car.carId(), List.of());
                for (Reservation res : carReservations) {
                    ReservationClassifier.Type type = ReservationClassifier.classify(res, from, to);

                    if (type == ReservationClassifier.Type.HARD_OVERLAP) {
                        isAvailable = false;
                        conflicts++;
                    } else if (type == ReservationClassifier.Type.DATA_CONFLICT) {
                        log.warn("Reservation has invalid or missing dates for reservationId={}, carId={}",
                                res.reservationId(),
                                res.carId());
                        conflicts++;
                    } else if (type == ReservationClassifier.Type.SOFT_OVERLAP) {
                        conflicts++;
                    }
                }
            }

            BigDecimal estimatedPrice = null;
            if (isAvailable && car.pricePerDay() != null && car.pricePerDay().compareTo(BigDecimal.ZERO) > 0) {
                estimatedPrice = car.pricePerDay().multiply(BigDecimal.valueOf(days));
            }

            return new AvailabilityResponseDto(
                    car.carId(),
                    car.model(),
                    isAvailable,
                    conflicts,
                    estimatedPrice);
        }).toList();
    }
}
