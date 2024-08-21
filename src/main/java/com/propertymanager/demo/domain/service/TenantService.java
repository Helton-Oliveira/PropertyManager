package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.TenantRepository;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import com.propertymanager.demo.mappers.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService extends ServiceImpl<Tenant, Long, UserResponse, UserRequest> {

    @Autowired
    private TenantRepository tenantRepository;

    public TenantService(TenantMapper mapper) {
        super(Tenant.class, UserResponse.class, mapper);
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

    public Tenant getReferenceTenantById(Long id) {
        if(!tenantRepository.existsById(id)) {
            throw new ValidateException("ERRO! ID do inquilino informado nào existe");
        }
        return tenantRepository.getReferenceById(id);
    }
}
