package com.propertymanager.demo.domain.database.entity;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "property")
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Property {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Addres addres;

    @Enumerated(EnumType.STRING)
    private TypeProperty typeProperty;
    private String rentalValue;
    private String description;
    private Boolean rented;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Property(PropertyRequest req, Owner owner) {
        this.typeProperty = req.getTypeProperty();
        this.rented = false;
        this.rentalValue = req.getRentalValue();
        this.owner = owner;
        this.description = req.getDescription();
        this.addres = new Addres(req.getAddressDto());
    }

    public void toHire() {
        this.rented = true;
    }
}
