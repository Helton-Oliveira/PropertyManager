package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getOne(Long id) {
        var tenant = userRepository.searchForTenantById(id);
        return new UserResponse(tenant);
    }
}
