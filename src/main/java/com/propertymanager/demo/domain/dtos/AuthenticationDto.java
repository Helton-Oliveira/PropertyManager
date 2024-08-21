package com.propertymanager.demo.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
        @Email @NotBlank
        String email,

        @NotBlank
        String password) {
}
