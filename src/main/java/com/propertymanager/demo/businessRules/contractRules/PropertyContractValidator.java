package com.propertymanager.demo.businessRules.contractRules;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyContractValidator implements ContractRules{

    @Autowired
    ContractRepository repository;

    @Override
    public void valid(Contract data) {
        var contract = repository.searchForPropertyContract(data.getProperty().getId());

        System.out.println(contract);

        if(contract != null) {
            if (contract.getValidity().isBefore(data.getValidity())) {
                throw new ValidateException("Erro ao criar contrato! Propriedade contem um contrato existente até " + contract.getValidity());
            }
        }
    }
}
