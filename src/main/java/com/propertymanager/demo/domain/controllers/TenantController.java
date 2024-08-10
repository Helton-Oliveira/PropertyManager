package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/q")
    public ResponseEntity<Page<UserResponse>> findUserByCriteria(@PageableDefault(size = 10) Pageable page, @RequestParam Map<String, String> req){
        var response = tenantService.findByCriteria(page, req);
        return ResponseEntity.ok(response);
    }

}
