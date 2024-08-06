package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.service.PropertyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
public class PropertyController extends Controller<Property, Long, PropertyResponse, PropertyRequest> {

    public PropertyController(PropertyService propertyService) {
        this.service = propertyService;
    }

}
