package com.propertymanager.demo.domain.dtos;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.entity.Property;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyResponse {

    private Long id;
    private TypeProperty typeProperty;
    private String rentalValue;
    private String description;
    private Boolean rented;

    @JsonAlias({"owner", "id"})
    private Owner ownerId;

    @JsonAlias("address")
    private AddressDto addressDto;

    public PropertyResponse(Property property, PropertyRequest req, Owner owner) {
        this.id = property.getId();
        this.rented = property.getRented();
        this.typeProperty = req.getTypeProperty();
        this.rentalValue = req.getRentalValue();
        this.description = req.getDescription();
        this.ownerId = owner;
        this.addressDto = req.getAddressDto();
    }


    public PropertyResponse(Property property) {
        this.id = property.getId();
        this.typeProperty = property.getTypeProperty();
        this.rentalValue = property.getRentalValue();
        this.description = property.getDescription();
        this.ownerId = property.getOwner();
        this.rented = property.getRented();
        this.addressDto = new AddressDto(property.getAddress());
    }
}
