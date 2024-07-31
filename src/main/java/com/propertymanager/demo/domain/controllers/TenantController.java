package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;


    @GetMapping("/{id}")
    public ResponseEntity getOneTenant(@PathVariable Long id) {
        var tenant = tenantService.getOne(id);
        return ResponseEntity.ok(tenant);
    }
}
