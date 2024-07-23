package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.endereco.Addres;
import jakarta.persistence.*;
import lombok.*;

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
    private TypeProperty type;

    private String rentalValue;
    private String description;
    private Boolean rented;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @OneToOne(mappedBy = "property")
    private Contract contract;
}
