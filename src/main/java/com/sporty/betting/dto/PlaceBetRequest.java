package com.sporty.betting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PlaceBetRequest(

        @NotNull(message = "Event ID cannot be null")
        @Positive(message = "Event ID must be greater than zero")
        Long eventId,

        @NotNull(message = "Driver ID cannot be null")
        @Positive(message = "Driver ID must be greater than zero")
        Long driverId,

        @Positive(message = "Odds must be a positive value")
        Integer odds,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be a positive value")
        BigDecimal amount
) {}

