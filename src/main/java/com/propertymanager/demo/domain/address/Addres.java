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

    public Addres(AddressDto data) {
        this.publicPlace = data.publicPlace();
        this.neighborhood = data.neighborhood();
        this.zipCode = data.zipCode();
        this.uf = data.uf();
        this.city = data.city();
        this.number = data.number();
    }
}
