package com.propertymanager.demo.businessRules.propertyRules;

import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import org.springframework.stereotype.Component;

@Component
public class RentalValueMustBeGreatherThanZero implements PropertyRules {
    @Override
    public void valid(PropertyRequest data) {
        var rentalValue = Double.valueOf(data.getRentalValue());
        if(rentalValue <= 0) {
            throw new ValidateException("ERRO! o valor da propriedade deve ser maior que 0");
        }
    }
}
