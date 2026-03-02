package com.example.backend.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public enum ReservationStatus {
    CONFIRMED,
    PICKED_UP,
    CANCELLED,
    UNKNOWN;

    private static final Logger log = LoggerFactory.getLogger(ReservationStatus.class);

    public static ReservationStatus fromString(String status) {
        if (status == null || status.isBlank()) {
            return UNKNOWN;
        }
        try {
            return ReservationStatus.valueOf(status.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            log.warn("Unknown reservation status received: {}", status);
            return UNKNOWN;
        }
    }
}
