package com.sporty.betting.dto;

import java.math.BigDecimal;
import java.util.List;

public record UserDTO(
     Long id,
     String name,
     BigDecimal balance,
     List<BetDTO> bets){
}

