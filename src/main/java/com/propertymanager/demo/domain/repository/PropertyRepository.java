package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
