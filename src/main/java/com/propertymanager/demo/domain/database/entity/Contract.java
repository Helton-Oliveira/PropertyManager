package com.propertymanager.demo.domain.database.entity;

import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.utils.DateUtils;
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

    public Contract(Property property, Tenant tenant, ContractRequest req) {
        this.tenant = tenant;
        this.property = property;
         this.status = true;
        this.negotiatedPrice = property.getRentalValue();
        this.created = LocalDateTime.now();
        this.validity = DateUtils.calculateFutureDate(this.getCreated(), req.getContractPeriod());
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", tenant=" + tenant +
                ", property=" + property +
                ", status=" + status +
                ", negotiatedPrice='" + negotiatedPrice + '\'' +
                ", created=" + created +
                ", validity=" + validity +
                '}';
    }
}
