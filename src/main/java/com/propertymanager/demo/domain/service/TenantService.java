/*
package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public UserResponse getTenantDetailsById(Long id) {
        var tenant = tenantRepository.getReferenceById(id);
        return new UserResponse(tenant);
    }

    public UserResponse updateTenantAccount(UserRequest req, Long id) {
        var tenant = tenantRepository.getReferenceById(id);
        tenant.updateInfo(req);
        tenantRepository.save(tenant);

        return new UserResponse(tenant);
    }
}
*/
