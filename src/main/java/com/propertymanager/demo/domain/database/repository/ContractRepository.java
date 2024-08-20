package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT * FROM contracts WHERE tenant_id = :tenantId", nativeQuery = true)
    Contract searchForTenantContract(Long tenantId);

    @Query(value = "SELECT * FROM contracts WHERE property_id = :propertyId", nativeQuery = true)
    Contract searchForPropertyContract(Long propertyId);
}
