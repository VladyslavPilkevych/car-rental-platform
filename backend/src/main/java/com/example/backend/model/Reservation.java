package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Reservation(
                String reservationId,
                String carId,
                String from,
                String to,
                String status) {
}
