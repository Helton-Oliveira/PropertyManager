package com.propertymanager.demo.domain.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propertymanager.demo.domain.abstractModels.ContractPeriod;
import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.entity.Tenant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractResponse {

    private Long id;

    @JsonAlias("tenant")
    private Tenant tenantId;

    @JsonAlias("property")
    private Property propertyId;
    private String negotiatedPrice;
    private Boolean status;
    private LocalDateTime created;
    private ContractPeriod period;


    private LocalDate validity;

    public ContractResponse(Contract contract, ContractRequest req, Property property) {
        this.id = contract.getId();
        this.tenantId = contract.getTenant();
        this.propertyId = property;
        this.negotiatedPrice = property.getRentalValue();
        this.status = true;
        this.created = contract.getCreated();
        this.validity = contract.getValidity();
        this.period = req.getContractPeriod();
    }

    public ContractResponse(Contract contract) {
        this.id = contract.getId();
        this.tenantId = contract.getTenant();
        this.propertyId = contract.getProperty();
        this.negotiatedPrice = contract.getNegotiatedPrice();
        this.status = contract.getStatus();
        this.created = contract.getCreated();
        this.validity = contract.getValidity();
    }
}
