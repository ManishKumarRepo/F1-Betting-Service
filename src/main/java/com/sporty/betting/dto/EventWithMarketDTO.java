package com.sporty.betting.dto;

import java.util.List;

public record EventWithMarketDTO(
        F1EventDTO event,
        List<DriverMarketDTO> drivers
) {}

