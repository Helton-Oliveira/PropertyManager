package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest {

    @NotNull
    private TypeProperty typeProperty;

    @NotBlank
    private String rentalValue;

    @NotBlank
    private String description;

    @NotNull(message = "ERROR! OwnerId is mandatory")
    private Long ownerId;

    @NotNull @Valid
    private AddressDto addressDto;
}
