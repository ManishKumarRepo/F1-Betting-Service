package com.sporty.betting.controller;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.PlaceBetRequest;
import com.sporty.betting.service.BettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bets")
@Tag(name = "C) PLACE BET API", description = "ENDPOINT FOR PLACING A BET ON EVENT.")
public class BettingController {

    private final BettingService bettingService;

    public BettingController(BettingService bettingService) {
        this.bettingService = bettingService;
    }

    @Operation(
            summary = "REGISTERED USER PLACE BET REQUEST"
    )
    @PostMapping("/{userId}")
    public BetDTO placeBet(@PathVariable Long userId, @Valid @RequestBody PlaceBetRequest req) {
        return bettingService.placeBet(userId, req);
    }
}

