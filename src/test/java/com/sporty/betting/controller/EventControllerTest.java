package com.sporty.betting.controller;

import com.sporty.betting.dto.DriverMarketDTO;
import com.sporty.betting.dto.EventWithMarketDTO;
import com.sporty.betting.dto.F1EventDTO;
import com.sporty.betting.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    void listEvents_success() throws Exception {
        List<F1EventDTO> events = List.of(
                new F1EventDTO(10L, "Bahrain GP", "Bahrain", 2024)
        );

        when(eventService.listEvents(any())).thenReturn(events);

        mockMvc.perform(get("/api/events")
                        .param("sessionType", "RACE")
                        .param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].session_key").value(10))
                .andExpect(jsonPath("$[0].session_name").value("Bahrain GP"));
    }

    @Test
    void getDrivers_success() throws Exception {
        EventWithMarketDTO dto = new EventWithMarketDTO(
                new F1EventDTO(10L, "Bahrain GP", "Bahrain", 2024),
                List.of(new DriverMarketDTO(44L, "Lewis Hamilton", 200))
        );

        when(eventService.listEventWithDriverData(10L))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/events/10/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].event.session_key").value(10))
                .andExpect(jsonPath("$[0].drivers[0].driverId").value(44));
    }
}
