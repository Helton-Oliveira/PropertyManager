package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
