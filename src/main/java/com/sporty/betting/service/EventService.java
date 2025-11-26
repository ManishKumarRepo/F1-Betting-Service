package com.sporty.betting.service;

import com.sporty.betting.dto.EventFilter;
import com.sporty.betting.dto.EventResultDTO;
import com.sporty.betting.dto.EventWithMarketDTO;
import com.sporty.betting.dto.F1EventDTO;

import java.util.List;

public interface EventService {
    List<F1EventDTO> listEvents(EventFilter filter);
    List<EventWithMarketDTO> listEventWithDriverData(Long eventId);
    List<EventResultDTO> eventResult(Long eventId);
}
