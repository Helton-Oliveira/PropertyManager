package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.address.Addres;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

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

    @NotBlank
    private String number;


    public AddressDto(Addres addres) {
        this.publicPlace = addres.getPublicPlace();
        this.neighborhood = addres.getNeighborhood();
        this.zipCode = addres.getZipCode();
        this.city = addres.getCity();
        this.uf = addres.getUf();
        this.number = addres.getNumber();
    }
}
