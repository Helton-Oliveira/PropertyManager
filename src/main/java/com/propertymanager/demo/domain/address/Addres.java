package com.propertymanager.demo.domain.address;

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

}
