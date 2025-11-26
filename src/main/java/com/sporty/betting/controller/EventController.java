package com.sporty.betting.controller;

import com.sporty.betting.dto.EventFilter;
import com.sporty.betting.dto.EventWithMarketDTO;
import com.sporty.betting.dto.F1EventDTO;
import com.sporty.betting.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "B) EVENTS API", description = "ENDPOINT FOR FETCHING  EVENTS/SESSION AND ASSOCIATED DRIVERS.")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "LIST ALL EVENTS (FILTER BY SESSION TYPE, YEAR, COUNTRY)"
    )
    @GetMapping
    public List<F1EventDTO> getEvents(EventFilter filter) {
        return eventService.listEvents(filter);
    }

    @Operation(
            summary = "DRIVERS ASSOCIATED WITH EVENT"
    )
    @GetMapping("/{eventId}/drivers")
    public List<EventWithMarketDTO> getEventsWithDrivers(@PathVariable("eventId") Long eventId) {
        return eventService.listEventWithDriverData(eventId);
    }
}

