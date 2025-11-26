package com.sporty.betting.controller;

import com.sporty.betting.dto.*;
import com.sporty.betting.service.OutcomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(OutcomeController.class)
class OutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OutcomeService outcomeService;

    @Test
    void processOutcome_success() throws Exception {
        EventResultDTO result = new EventResultDTO(
                1, 44L, 100L, LocalDateTime.now()
        );

        EventOutcomeResponse response = new EventOutcomeResponse(result, List.of());

        when(outcomeService.processOutcome(any())).thenReturn(response);

        mockMvc.perform(post("/api/outcomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"eventId": 100}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventResult.driver_number").value(44));
    }
}
