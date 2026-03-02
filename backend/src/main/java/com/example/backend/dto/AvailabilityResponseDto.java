package com.example.backend.dto;

import java.math.BigDecimal;

public record AvailabilityResponseDto(
                String carId,
                String model,
                boolean isAvailable,
                int conflicts,
                BigDecimal estimatedPrice) {
}
