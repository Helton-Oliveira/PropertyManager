package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.TenantResponse;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public TenantResponse getTenantDetailsById(Long id) {
        var tenant = tenantRepository.getReferenceById(id);
        return new TenantResponse(tenant);
    }

   // public Page<TenantResponse> fetchAllTenants(Pageable page) {
       // return tenantRepository.findByActiveTrue(page).map(TenantResponse::new);
    //}

    public TenantResponse updateTenantAccount(UserRequest req, Long id) {
        var tenant = tenantRepository.getReferenceById(id);
        tenant.updateInfo(req);
        tenantRepository.save(tenant);

        return new TenantResponse(tenant);
    }
}
