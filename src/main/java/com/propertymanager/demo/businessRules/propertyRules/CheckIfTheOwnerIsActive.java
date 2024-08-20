package com.propertymanager.demo.businessRules.propertyRules;

import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckIfTheOwnerIsActive implements PropertyRules {

    @Autowired
    OwnerRepository ownerRepository;

    @Override
    public void valid(PropertyRequest data) {
        var owner = ownerRepository.getReferenceById(data.getOwnerId());

        if (!owner.getActive()) {
            throw new ValidateException("ERRO! Proprietário informado nào está mais ativo no sistema.");
        }
    }
}
