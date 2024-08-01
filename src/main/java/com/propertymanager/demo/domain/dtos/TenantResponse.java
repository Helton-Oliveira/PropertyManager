package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.entity.Tenant;

public record TenantResponse(Long id,
                             String name,
                             String cpf,
                             String email,
                             String phone) {

    public TenantResponse(Tenant tenant) {
        this(tenant.getId(), tenant.getName(), tenant.getCpf(), tenant.getEmail(), tenant.getPhone());
    }
}
