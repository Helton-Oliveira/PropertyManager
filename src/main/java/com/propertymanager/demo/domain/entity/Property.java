package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
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

    @OneToMany(mappedBy = "property")
    private List<Contract> contract;
}
