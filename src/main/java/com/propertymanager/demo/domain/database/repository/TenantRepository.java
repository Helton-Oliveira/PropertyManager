package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    Page<Tenant> findByActiveTrue(Pageable page);

    @Query("SELECT t FROM Tenant t WHERE t.id = :id AND t.active = true")
    Optional<Tenant> searchById(Long id);

    Tenant findByEmail(String email);
}
