package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.abstractModels.ContractPeriod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractRequest {

    @NotNull
    private Long tenantId;

    @NotNull
    private Long propertyId;

    @NotNull
    private Integer contractPeriod;

    public ContractPeriod getContractPeriod() {
        return ContractPeriod.fromCode(this.contractPeriod);
    }
}
