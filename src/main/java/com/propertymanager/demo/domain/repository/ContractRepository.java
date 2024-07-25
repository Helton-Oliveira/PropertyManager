package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
