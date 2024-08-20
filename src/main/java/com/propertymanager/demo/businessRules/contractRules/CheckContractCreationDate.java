package com.propertymanager.demo.businessRules.contractRules;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckContractCreationDate implements ContractRules{

    @Autowired
    ContractRepository repository;

    @Override
    public void valid(Contract data) {
        var tenantContract = repository.searchForTenantContract(data.getTenant().getId());
        var propertyContract = repository.searchForPropertyContract(data.getProperty().getId());
        var creationDate = data.getCreated().toLocalDate();

        if (tenantContract != null) {
            if (creationDate.equals(tenantContract.getValidity())) {
                throw new ValidateException("ERRO! Contrato só pode ser criado após o término do ultimo.");
            }
        }

        if (propertyContract != null) {
            if (creationDate.equals(propertyContract.getValidity())) {
                throw new ValidateException("ERRO! Contrato só pode ser criado após o término do ultimo.");
            }
        }
    }
}
