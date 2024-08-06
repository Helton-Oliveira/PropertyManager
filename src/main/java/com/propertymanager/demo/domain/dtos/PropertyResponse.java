package com.propertymanager.demo.domain.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.database.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyResponse {

    private Long id;
    private TypeProperty typeProperty;
    private String rentalValue;
    private String description;
    private Boolean rented;
    private Long ownerId;
    private AddressDto addressDto;

    public PropertyResponse(Property id, PropertyRequest req) {
        this.id = id.getId();
        this.typeProperty = req.getTypeProperty();
        this.rentalValue = req.getRentalValue();
        this.description = req.getDescription();
        this.ownerId = req.getOwnerId();
        this.addressDto = req.getAddressDto();
    }


}
