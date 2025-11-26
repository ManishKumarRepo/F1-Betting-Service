package com.sporty.betting.service;

import com.sporty.betting.dto.*;
import com.sporty.betting.service.external.EventProviderFactory;
import com.sporty.betting.service.external.F1EventProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventProviderFactory factory;

    @Mock
    private F1EventProvider provider;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void listEvents_success() {
        when(factory.getProvider(any())).thenReturn(provider);
        when(provider.getEvents(any())).thenReturn(
                List.of(new F1EventDTO(10L, "Bahrain GP", "Bahrain", 2024))
        );

        List<F1EventDTO> events = eventService.listEvents(new EventFilter("RACE", 2024, "Bahrain"));

        assertEquals(1, events.size());
    }

    @Test
    void getEventDrivers_success() {
        when(factory.getProvider(any())).thenReturn(provider);

        when(provider.getEventsByKey(10L)).thenReturn(
                List.of(new F1EventDTO(10L, "Bahrain GP", "Bahrain", 2024))
        );

        when(provider.getDrivers(10L)).thenReturn(
                List.of(new DriverDTO(44L, "Lewis Hamilton"))
        );

        List<EventWithMarketDTO> eventData = eventService.listEventWithDriverData(10L);

        assertEquals(1, eventData.size());
        assertEquals(10L, eventData.get(0).event().session_key());
    }
}