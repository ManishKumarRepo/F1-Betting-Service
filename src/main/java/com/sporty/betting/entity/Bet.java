package com.sporty.betting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bets")
@Getter
@Setter
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Long driverId;
    private Integer odds;

    @Enumerated(EnumType.STRING)
    private BetStatus status = BetStatus.PENDING;

    private BigDecimal amount;
    private BigDecimal payout;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
