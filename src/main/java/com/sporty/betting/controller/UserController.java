package com.sporty.betting.controller;

import com.sporty.betting.dto.UserDTO;
import com.sporty.betting.dto.UserRegisterRequestDTO;
import com.sporty.betting.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "A) USER REGISTRATION", description = "ENDPOINTS FOR USER REGISTER AND FETCHING DETAILS.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "REGISTER USER"
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO req) {
        log.info("Register user {}", req);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(req));
    }

    @Operation(
            summary = "GET USER AND BETS ASSOCIATED WITH USER"
    )
    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        log.info("Fetching user details for userId={}", userId);
        return userService.getUser(userId);
    }
}
