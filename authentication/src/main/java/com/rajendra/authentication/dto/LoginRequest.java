package com.rajendra.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for user login")
public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        @Schema(description = "Username of the user", example = "rajat7891")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Schema(description = "Password of the user", example = "P@ssw0rd")
        String password
) {}