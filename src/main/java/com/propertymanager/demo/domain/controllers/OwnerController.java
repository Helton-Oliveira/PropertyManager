package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController extends Controller<Owner, Long, UserResponse, UserRequest>{

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.service = ownerService;
    }
}
