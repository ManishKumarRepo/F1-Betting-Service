package com.sporty.betting.dto;

import com.sporty.betting.entity.EventOutcome;

import java.time.LocalDateTime;

public record EventResultDTO(Integer position, Long driver_number, Long session_key, LocalDateTime createdAt) {
    public static EventResultDTO toDto(EventOutcome eventOutcome) {
        return new EventResultDTO(1, eventOutcome.getWinningDriverId(), eventOutcome.getEventId(), eventOutcome.getProcessedAt());
    }

    public static EventOutcome fromDto(EventResultDTO eventResultDTO) {
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(eventResultDTO.session_key());
        eventOutcome.setWinningDriverId(eventResultDTO.driver_number);
        eventOutcome.setProcessedAt(LocalDateTime.now());
        return eventOutcome;
    }
}
