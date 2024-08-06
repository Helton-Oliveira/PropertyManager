package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Owner;
import org.springframework.stereotype.Service;

@Service
public class OwnerService extends ServiceImpl<Owner, Long,UserResponse, UserRequest> {

    public OwnerService() {
        super(UserResponse.class, Owner.class);
    }
}
