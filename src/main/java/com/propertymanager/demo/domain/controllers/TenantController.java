package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.entity.Tenant;
import com.propertymanager.demo.domain.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/tenant")
public class TenantController extends Controller<Tenant, Long, UserResponse, UserRequest> {

    @Autowired
    private TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.service = tenantService;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity deleteEntity(Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
