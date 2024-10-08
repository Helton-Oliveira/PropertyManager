package com.propertymanager.demo.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

        @NotBlank
        private String name;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        private String cpf;

        @NotBlank
        private String phone;
}

