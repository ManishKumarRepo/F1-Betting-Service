package com.sporty.betting.service;

import com.sporty.betting.dto.*;
import com.sporty.betting.service.external.EventProviderFactory;
import com.sporty.betting.service.external.F1EventProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventProviderFactory factory;

    public EventServiceImpl(EventProviderFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<F1EventDTO> listEvents(EventFilter filter) {
        log.info("Fetch Events for Filter :{}", filter);
        F1EventProvider provider = factory.getProvider("OpenF1EventProvider");
        return provider.getEvents(filter);
    }

    @Override
    public List<EventWithMarketDTO> listEventWithDriverData(Long eventId) {
        F1EventProvider provider = factory.getProvider("OpenF1EventProvider");
        log.info("Fetch Events for id :{}", eventId);

        List<F1EventDTO> events = provider.getEventsByKey(eventId);

        Map<Long, F1EventDTO> groupingBySessionKey = events.stream().collect(Collectors.toMap(F1EventDTO::session_key, Function.identity(), (x, y) -> y));

        log.info("groupingBySessionKey: {}", groupingBySessionKey);
        return groupingBySessionKey.keySet().stream().map(event -> {
            log.info("Fetch Drivers for event :{}", eventId);

            List<DriverDTO> drivers = provider.getDrivers(event);

            List<DriverMarketDTO> market = drivers.stream().map(d ->
                    new DriverMarketDTO(
                            d.driver_number(),
                            d.full_name(),
                            List.of(2, 3, 4).get(new Random().nextInt(3))
                    )
            ).toList();

            return new EventWithMarketDTO(groupingBySessionKey.get(event), market);
        }).toList();
    }

    @Override
    public List<EventResultDTO> eventResult(Long eventId) {
        F1EventProvider provider = factory.getProvider("OpenF1EventProvider");
        log.info("Fetch Event Outcome for id :{}", eventId);
        return provider.getEventResultDetails(eventId);
    }
}
