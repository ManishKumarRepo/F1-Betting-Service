package com.sporty.betting.dto;

import java.util.List;

public record EventOutcomeResponse(EventResultDTO eventResult, List<BetDTO> betsWon) {
}
