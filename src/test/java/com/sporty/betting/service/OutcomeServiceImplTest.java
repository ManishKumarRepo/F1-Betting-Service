package com.sporty.betting.service;

import com.sporty.betting.dto.*;
import com.sporty.betting.entity.Bet;
import com.sporty.betting.entity.User;
import com.sporty.betting.repository.BetRepository;
import com.sporty.betting.repository.EventOutcomeRepository;
import com.sporty.betting.service.external.EventProviderFactory;
import com.sporty.betting.service.external.F1EventProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class OutcomeServiceImplTest {

    @Mock
    private BetRepository betRepo;

    @Mock
    private EventOutcomeRepository outcomeRepo;

    @Mock
    private EventProviderFactory factory;

    @Mock
    private F1EventProvider provider;

    @InjectMocks
    private OutcomeServiceImpl outcomeService;

    @Test
    void processOutcome_winnerFound() {
        EventOutcomeRequest req = new EventOutcomeRequest(100L);

        EventResultDTO winner = new EventResultDTO(
                1, 44L, 100L, LocalDateTime.now()
        );

        when(outcomeRepo.existsByEventId(100L)).thenReturn(false);

        when(factory.getProvider(any())).thenReturn(provider);
        when(provider.getEventResultDetails(100L)).thenReturn(List.of(winner));

        Bet winningBet = new Bet();
        winningBet.setEventId(100L);
        winningBet.setDriverId(44L);
        winningBet.setAmount(BigDecimal.valueOf(10));
        winningBet.setOdds(200);
        User user = new User();
        user.setBalance(BigDecimal.valueOf(0));
        winningBet.setUser(user);

        when(betRepo.findByEventIdWithUser(100L)).thenReturn(List.of(winningBet));

        EventOutcomeResponse response = outcomeService.processOutcome(req);

        assertEquals(1, response.betsWon().size());
        assertEquals(BigDecimal.valueOf(2000), response.betsWon().get(0).payout());
    }
}

