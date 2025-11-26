package com.sporty.betting.repository;

import com.sporty.betting.entity.EventOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventOutcomeRepository extends JpaRepository<EventOutcome, Long> {
    public boolean existsByEventId(Long eventId);

    public List<EventOutcome> findByEventId(Long event);
}

