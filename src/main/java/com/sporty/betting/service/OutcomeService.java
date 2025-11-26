package com.sporty.betting.service;

import com.sporty.betting.dto.EventOutcomeRequest;
import com.sporty.betting.dto.EventOutcomeResponse;

public interface OutcomeService {
    EventOutcomeResponse processOutcome(EventOutcomeRequest req);
}
