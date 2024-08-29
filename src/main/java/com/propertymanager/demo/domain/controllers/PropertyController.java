package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.domain.service.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/property")
@SecurityRequirement(name = "bearer-key")
public class PropertyController extends Controller<Property, Long, PropertyResponse, PropertyRequest> {

    @Autowired
    PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.service = propertyService;
    }

    @GetMapping("/location/address")
    public ResponseEntity<Page<PropertyResponse>> getPropertiesByLocation(@RequestParam Map<String, String> queryParams, @PageableDefault(size = 10) Pageable page) {
        var response = propertyService.filterByLocation(queryParams, page);

        return ResponseEntity.ok(response);
    }


}
