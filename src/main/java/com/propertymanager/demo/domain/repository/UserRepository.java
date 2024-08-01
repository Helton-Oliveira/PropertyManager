package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
