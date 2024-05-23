package com.rajendra.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfileDTO(
    @NotBlank(message = "First name is mandatory") String firstName,
    @NotBlank(message = "Last name is mandatory") String lastName,
    String phoneNumber,
    String address
) {}

