/*package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.service.TenantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/{id}")
    public ResponseEntity getOneTenant(@PathVariable Long id) {
        var tenant = tenantService.getTenantDetailsById(id);
        return ResponseEntity.ok(tenant);
    }

    @PutMapping("/id")
    @Transactional
    public ResponseEntity updateTenant(@PathVariable Long id, @RequestBody UserRequest req) {
        var response = tenantService.updateTenantAccount(req, id);

        return ResponseEntity.ok(response);
    }
}*/
