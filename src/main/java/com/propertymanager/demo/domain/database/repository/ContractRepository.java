package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
