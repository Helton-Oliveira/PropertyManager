package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Page<Tenant> findByActiveTrue(Pageable page);
}
