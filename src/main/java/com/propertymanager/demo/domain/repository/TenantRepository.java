package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
