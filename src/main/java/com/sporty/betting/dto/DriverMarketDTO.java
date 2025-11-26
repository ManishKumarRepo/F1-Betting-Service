package com.sporty.betting.dto;

public record DriverMarketDTO(
        Long driverId,
        String fullName,
        Integer odds
) {}

