package com.propertymanager.demo.businessRules.contractRules;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.domain.service.TenantService;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantContractValidator implements ContractRules {

    @Autowired
    ContractRepository repository;

    @Autowired
    TenantService tenantService;

    @Override
    public void valid(Contract data) {
        var contract = repository.searchForTenantContract(data.getTenant().getId());
        var tenant = tenantService.getReferenceTenantById(data.getTenant().getId());

        if(contract != null) {
            if (contract.getValidity().isBefore(data.getValidity())) {
                throw new ValidateException("Erro ao criar contrato! Inquilino contem um contrato existente até " + contract.getValidity());
            }
        }

        if(!tenant.getActive()) {
            throw new ValidateException("ERRO! Inquilino esta inativo no sistema.");
        }
    }
}
