package com.sporty.betting.service.external;

import com.sporty.betting.dto.DriverDTO;
import com.sporty.betting.dto.EventFilter;
import com.sporty.betting.dto.EventResultDTO;
import com.sporty.betting.dto.F1EventDTO;
import com.sporty.betting.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.Optional;

@Service("openF1Provider")
@Slf4j
public class OpenF1EventProvider implements F1EventProvider {

    private final RestClient restClient;

    private static final DefaultUriBuilderFactory uriFactory =
            new DefaultUriBuilderFactory("https://api.openf1.org/v1");

    public OpenF1EventProvider(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<F1EventDTO> getEvents(EventFilter filter) {
        UriBuilder builder = uriFactory.builder()
                .path("/sessions");

        filter.sessionTypeFilter().filter(sessionType -> !sessionType.isBlank()).ifPresent(sessionType -> builder.queryParam("session_type", sessionType));
        filter.countryFilter().filter(country -> !country.isBlank()).ifPresent(country -> builder.queryParam("country_name", country));
        filter.yearFilter().filter(year -> year != 0).ifPresent(year -> builder.queryParam("year", year));

        return fetchEvents(builder, "External OpenF1 API Sessions");
    }

    @Override
    public List<F1EventDTO> getEventsByKey(Object key) {
        if (key == null) {
            throw new BadRequestException("EventId should not be empty");
        }

        UriBuilder builder = uriFactory.builder()
                .path("/sessions")
                .queryParam("session_key", key);

        return fetchEvents(builder, "External OpenF1 API Sessions(byId)");
    }

    /**
     * Shared method to call the Events external API.
     */
    private List<F1EventDTO> fetchEvents(UriBuilder builder, String logPrefix) {
        String url = builder.build().toString();

        log.info("{} : {}", logPrefix, url);

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<DriverDTO> getDrivers(Long eventId) {
        String url = uriFactory.builder()
                .path("/drivers")
                .queryParamIfPresent("session_key", Optional.of(eventId))
                .build()
                .toString();

        log.info("External OpenF1 API Drivers(bySessionKey) : {}", url);

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<EventResultDTO> getEventResultDetails(Long eventId) {
        String url = uriFactory.builder()
                .path("/session_result")
                .queryParamIfPresent("session_key", Optional.of(eventId))
                .queryParam("position", 1)
                .build()
                .toString();

        log.info("External OpenF1 API Session Results(byId) : {}", url);

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
