package com.sporty.betting.controller;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.PlaceBetRequest;
import com.sporty.betting.entity.BetStatus;
import com.sporty.betting.service.BettingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BettingController.class)
class BettingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BettingService bettingService;

    @Test
    void placeBet_success() throws Exception {
        BetDTO bet = new BetDTO(
                1L, 100L, 44L, 200,
                BigDecimal.valueOf(50),
                BetStatus.PENDING,
                BigDecimal.ZERO
        );

        PlaceBetRequest req = new PlaceBetRequest(100L, 44L, 200, BigDecimal.valueOf(50));

        when(bettingService.placeBet(eq(1L), any())).thenReturn(bet);

        mockMvc.perform(post("/api/bets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "eventId": 100,
                                  "driverId": 44,
                                  "odds": 200,
                                  "amount": 50
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(100))
                .andExpect(jsonPath("$.driverId").value(44))
                .andExpect(jsonPath("$.amount").value(50));
    }
}
