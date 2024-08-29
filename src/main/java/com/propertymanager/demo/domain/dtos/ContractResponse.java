package com.propertymanager.demo.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propertymanager.demo.domain.abstractModels.ContractPeriod;
import com.propertymanager.demo.domain.database.entity.Contract;
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
    private String negotiatedPrice;
    private Boolean status;
    private LocalDateTime created;
    private ContractPeriod period;
    private LocalDate validity;
    private Long propertyId;
    private Long tenantId;

    public ContractResponse(Contract contract, ContractRequest req) {
        this.id = contract.getId();
        this.tenantId = contract.getTenant().getId();
        this.propertyId = contract.getProperty().getId();
        this.negotiatedPrice = contract.getNegotiatedPrice();
        this.status = true;
        this.created = contract.getCreated();
        this.validity = contract.getValidity();
        this.period = req.getContractPeriod();
    }

}
