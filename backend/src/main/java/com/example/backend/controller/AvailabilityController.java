package com.example.backend.controller;

import com.example.backend.dto.AvailabilityResponseDto;
import com.example.backend.service.AvailabilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/availability")
    public List<AvailabilityResponseDto> getAvailability(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return availabilityService.checkAvailability(from, to);
    }
}
