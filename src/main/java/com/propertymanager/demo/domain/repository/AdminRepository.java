package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
