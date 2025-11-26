package com.sporty.betting.service;

import com.sporty.betting.dto.*;
import com.sporty.betting.entity.User;
import com.sporty.betting.exception.BadRequestException;
import com.sporty.betting.repository.BetRepository;
import com.sporty.betting.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class BettingServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private BettingServiceImpl bettingService;

    @Test
    void placeBet_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Alex");
        user.setBalance(BigDecimal.valueOf(100));

        PlaceBetRequest req = new PlaceBetRequest(
                100L, 44L, 200, BigDecimal.valueOf(50)
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        BetDTO dto = bettingService.placeBet(1L, req);

        assertEquals(44L, dto.driverId());
        assertEquals(BigDecimal.valueOf(50), user.getBalance());
        verify(betRepository, times(1)).save(any());
    }

    @Test
    void placeBet_insufficientBalance() {
        User user = new User();
        user.setBalance(BigDecimal.valueOf(10));

        PlaceBetRequest req = new PlaceBetRequest(
                100L, 44L, 200, BigDecimal.valueOf(50)
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () ->
                bettingService.placeBet(1L, req));
    }
}
