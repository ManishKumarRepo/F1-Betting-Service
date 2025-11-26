package com.sporty.betting.service;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.PlaceBetRequest;

public interface BettingService {
    BetDTO placeBet(Long userId, PlaceBetRequest request);
}
