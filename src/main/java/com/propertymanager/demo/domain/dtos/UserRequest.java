package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.abstractModels.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
        @NotBlank
        String name,

        @NotBlank @Email
        String email,

        @NotBlank
        String password,

        @NotBlank @Pattern(regexp = "\\d{4}")
        String cpf,

        @NotBlank
        String phone,

        @NotNull
        Role role
) {
}
