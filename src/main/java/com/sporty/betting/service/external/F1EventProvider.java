package com.sporty.betting.service.external;

import com.sporty.betting.dto.DriverDTO;
import com.sporty.betting.dto.EventFilter;
import com.sporty.betting.dto.EventResultDTO;
import com.sporty.betting.dto.F1EventDTO;

import java.util.List;

public interface F1EventProvider {
    List<F1EventDTO> getEvents(EventFilter filter);
    List<F1EventDTO> getEventsByKey(Object key);
    List<DriverDTO> getDrivers(Long eventId);
    List<EventResultDTO> getEventResultDetails(Long eventId);
}

