package com.sporty.betting.controller;

import com.sporty.betting.dto.EventOutcomeRequest;
import com.sporty.betting.dto.EventOutcomeResponse;
import com.sporty.betting.service.OutcomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/outcomes")
@Tag(name = "D) BET EVENT OUTCOME API", description = "ENDPOINT FOR GETTING BET WINNERS.")
public class OutcomeController {

    private final OutcomeService outcomeService;

    public OutcomeController(OutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @Operation(
            summary = "FETCH USERS WHO WON THE BET FOR EVENT"
    )
    @PostMapping
    public EventOutcomeResponse processOutcome(@Valid @RequestBody EventOutcomeRequest req) {
        return outcomeService.processOutcome(req);
    }
}

