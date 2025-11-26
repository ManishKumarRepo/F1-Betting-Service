package com.sporty.betting.dto;

import java.util.Optional;

public record EventFilter(
        String sessionType,
        Integer year,
        String country
) {
    public Optional<String> sessionTypeFilter() { return Optional.ofNullable(sessionType); }
    public Optional<Integer> yearFilter() { return Optional.ofNullable(year); }
    public Optional<String> countryFilter() { return Optional.ofNullable(country); }
}
