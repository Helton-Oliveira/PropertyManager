package com.propertymanager.demo.domain.address;

import com.propertymanager.demo.domain.dtos.AddressDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Addres {

    private String publicPlace;
    private String neighborhood;
    private String zipCode;
    private String number;
    private String city;
    private String uf;

    public Addres(AddressDto addressDto) {
        this.publicPlace = addressDto.getPublicPlace();
        this.neighborhood = addressDto.getNeighborhood();
        this.zipCode = addressDto.getZipCode();
        this.number = addressDto.getNumber();
        this.city = addressDto.getCity();
        this.uf = addressDto.getUf();
    }
}
