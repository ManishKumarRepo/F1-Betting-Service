package com.sporty.betting.dto;

public record F1EventDTO(
        Long session_key,
        String session_name,
        String country_name,
        Integer year
) {}
