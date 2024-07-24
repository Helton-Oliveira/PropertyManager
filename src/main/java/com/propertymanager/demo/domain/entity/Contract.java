package com.propertymanager.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "contract")
@Table(name = "contracts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Contract {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne()
    @JoinColumn(name = "property_id")
    private Property property;

    private Boolean status;
    private String negotiatedPrice;
    private LocalDateTime created;
    private LocalDate validity;

}
