package com.propertymanager.demo.businessRules.propertyRules;

import com.propertymanager.demo.domain.dtos.PropertyRequest;

public interface PropertyRules {
    void valid(PropertyRequest data);
}
