package com.sporty.betting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequestDTO(
        @NotNull(message = "User name cannot be null")
        @NotBlank(message = "User name cannot be blank")
        String name
) {
}
