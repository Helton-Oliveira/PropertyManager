package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService extends ServiceImpl<Tenant, Long, UserResponse, UserRequest> {

    @Autowired
    private TenantRepository tenantRepository;

    public TenantService() {
        super(UserResponse.class, Tenant.class);
    }

    @Override
    public boolean delete(Long id) {
        if (tenantRepository.existsById(id)) {
            var entity = tenantRepository.getReferenceById(id);
            entity.setActive(false);
            tenantRepository.save(entity);
            return true;
        }
        return false;
    }
}
