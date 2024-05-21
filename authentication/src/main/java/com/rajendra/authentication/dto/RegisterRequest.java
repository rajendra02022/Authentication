package com.rajendra.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Schema(description = "Request object for user registration")
public record RegisterRequest(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
        @Schema(description = "Username of the user", example = "rajat7891")
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email address")
        @Schema(description = "Email of the user", example = "rajat7891@example.com")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        @Schema(description = "Password of the user", example = "P@ssw0rd")
        String password,

        @NotBlank(message = "Confirm Password cannot be blank")
        @Schema(description = "Confirmation of the password", example = "P@ssw0rd")
        String confirmPassword
) {
    public RegisterRequest {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}

