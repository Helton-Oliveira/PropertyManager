package com.propertymanager.demo.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    @NotBlank
    private String publicPlace;

    @NotBlank
    private String neighborhood;

    @NotBlank @Pattern(regexp = "\\d{8}")
    private String zipCode;

    @NotBlank
    private String city;

    @NotBlank
    private String uf;

    private String number;
}
