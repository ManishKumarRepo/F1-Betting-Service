package com.sporty.betting.repository;

import com.sporty.betting.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query("SELECT b FROM Bet b JOIN FETCH b.user WHERE b.eventId = :eventId")
    List<Bet> findByEventIdWithUser(Long eventId);
}

