package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.AdminRequest;
import com.propertymanager.demo.domain.dtos.AdminResponse;
import com.propertymanager.demo.domain.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping()
    public ResponseEntity<Page<AdminResponse>> getAll(@PageableDefault(size = 10, sort = {"name"}) Pageable page) {
        var admins = adminService.getAllAdmins(page);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getOne(@PathVariable Long id) {
        var admin = adminService.getOneAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping()
    @Transactional()
    public ResponseEntity createAdmin(@RequestBody @Valid AdminRequest req, UriComponentsBuilder uriBuilde) {
        var adminCreated = adminService.created(req);
        var uri = uriBuilde.path("/admin/{id}").buildAndExpand(adminCreated.id()).toUri();

        return ResponseEntity.created(uri).body(adminCreated);
    }

}
