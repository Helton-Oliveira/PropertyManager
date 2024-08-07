package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.domain.database.repository.PropertyRepository;
import com.propertymanager.demo.domain.database.repository.TenantRepository;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractService extends ServiceImpl<Contract, Long, ContractResponse, ContractRequest> {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ContractRepository contractRepository;

    public ContractService() {
        super(ContractResponse.class, Contract.class);
    }

    @Override
    public ContractResponse save(ContractRequest req) {
        var tenant = tenantRepository.searchById(req.getTenantId());
        var property = propertyRepository.findById(req.getPropertyId());
        if(tenant.isPresent() && property.isPresent()) {
            var entityProperty = property.get();
             var contract = new Contract(entityProperty, tenant.get(), req);
             contractRepository.save(contract);
             entityProperty.toHire();
             propertyRepository.save(entityProperty);
             return new ContractResponse(contract, req, property.get());
        }
        return null;
    }

    @Override
    public Page<ContractResponse> findAll(Pageable page) {
        return contractRepository.findAll(page)
                .map(ContractResponse::new);
    }
}
