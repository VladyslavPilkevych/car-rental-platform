package com.example.backend.service;

import com.example.backend.dto.AvailabilityResponseDto;
import com.example.backend.model.Car;
import com.example.backend.model.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvailabilityServiceTest {

    private AvailabilityService availabilityService;

    @BeforeEach
    void setUp() {
        availabilityService = new AvailabilityService(new ObjectMapper());
        availabilityService.setCars(List.of(new Car("C-1", "Camry", "sedan", new BigDecimal("50"))));
    }

    @Test
    void testOverlapBoundary() {
        availabilityService.setReservations(List.of(
                new Reservation("R-1", "C-1", "2024-05-10", "2024-05-15", "CONFIRMED"),
                new Reservation("R-2", "C-1", "2024-05-20", "2024-05-25", "CONFIRMED")));

        List<AvailabilityResponseDto> result = availabilityService.checkAvailability("2024-05-15", "2024-05-20");

        assertEquals(1, result.size());
        AvailabilityResponseDto dto = result.get(0);
        assertEquals("C-1", dto.carId());
        assertEquals("Camry", dto.model());
        assertTrue(dto.isAvailable());
        assertEquals(0, dto.conflicts());
        assertEquals(new BigDecimal("250"), dto.estimatedPrice());
    }

    @Test
    void testConflictsCounting() {
        availabilityService.setReservations(List.of(
                new Reservation("R-H", "C-1", "2024-05-16", "2024-05-19", "CONFIRMED"),
                new Reservation("R-S", "C-1", "2024-05-17", "2024-05-18", "UNKNOWN"),
                new Reservation("R-D", "C-1", "2024-05-20", "2024-05-20", "CONFIRMED"),
                new Reservation("R-C", "C-1", "2024-05-15", "2024-05-20", "CANCELLED")));

        List<AvailabilityResponseDto> result = availabilityService.checkAvailability("2024-05-15", "2024-05-20");

        assertEquals(1, result.size());
        AvailabilityResponseDto dto = result.get(0);
        assertEquals("C-1", dto.carId());
        assertEquals("Camry", dto.model());
        assertFalse(dto.isAvailable());
        assertEquals(3, dto.conflicts());
        assertNull(dto.estimatedPrice());
    }
}