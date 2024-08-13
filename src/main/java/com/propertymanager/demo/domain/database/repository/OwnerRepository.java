package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.repository.custom.RepositoryCustom;
import com.propertymanager.demo.domain.dtos.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
}
