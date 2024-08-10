package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.service.OwnerService;
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
@RequestMapping("/owner")
public class OwnerController extends Controller<Owner, Long, UserResponse, UserRequest>{

    @Autowired
    OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.service = ownerService;
    }

    @GetMapping("/q")
    public ResponseEntity<Page<UserResponse>> findUserByCriteria(@PageableDefault(size = 10) Pageable page, @RequestParam Map<String, String> req){
        var response = ownerService.findByCriteria(page, req);
        return ResponseEntity.ok(response);
    }
}
