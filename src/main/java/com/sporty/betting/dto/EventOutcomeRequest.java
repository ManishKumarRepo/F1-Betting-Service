package com.sporty.betting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EventOutcomeRequest(
        @NotNull(message = "Event ID must not be null")
        @Positive(message = "Event ID must be greater than zero")
        Long eventId
) {}

