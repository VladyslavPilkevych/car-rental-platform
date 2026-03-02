package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Car(
        String carId,
        String model,
        @JsonProperty("class") String carClass,
        BigDecimal pricePerDay) {
}
