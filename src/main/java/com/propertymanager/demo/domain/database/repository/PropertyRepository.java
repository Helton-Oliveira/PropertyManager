package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
