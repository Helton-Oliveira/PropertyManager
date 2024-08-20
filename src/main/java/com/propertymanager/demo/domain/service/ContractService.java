package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.businessRules.contractRules.ContractRules;
import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import com.propertymanager.demo.infra.security.exceptioins.ValidateException;
import com.propertymanager.demo.mappers.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractService extends ServiceImpl<Contract, Long, ContractResponse, ContractRequest> {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private List<ContractRules> validators;

    public ContractService(ContractMapper mapper) {
        super(Contract.class, ContractResponse.class, mapper);
    }

    @Override
    public ContractResponse save(ContractRequest req) {
        var tenant = tenantService.getReferenceTenantById(req.getTenantId());
        var property = propertyService.getReferencePropertyById(req.getPropertyId());
        propertyService.upgradeToRented(property);

        var contract = new Contract(property, tenant, req);
        validators.forEach(v -> v.valid(contract));

        contractRepository.save(contract);
        return new ContractResponse(contract, req);
    }

    public Page<ContractResponse> filterByEntity(Map<String, String> req, Pageable page) {
        Map<String, String> modifiedParams = req.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey() + ".id",
                                Map.Entry::getValue
                ));
        System.out.println(modifiedParams);
        return super.findByCriteria(modifiedParams, page);
    }

}
