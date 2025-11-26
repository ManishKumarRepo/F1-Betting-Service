package com.sporty.betting.dto;

import com.sporty.betting.entity.Bet;
import com.sporty.betting.entity.BetStatus;

import java.math.BigDecimal;

public record BetDTO(
        Long userId,
        Long eventId,
        Long driverId,
        Integer odds,
        BigDecimal amount,
        BetStatus status,
        BigDecimal payout
) {
    public static BetDTO from(Bet bet) {
        return new BetDTO(
                bet.getUser().getId(),
                bet.getEventId(),
                bet.getDriverId(),
                bet.getOdds(),
                bet.getAmount(),
                bet.getStatus(),
                bet.getPayout()
        );
    }
}

