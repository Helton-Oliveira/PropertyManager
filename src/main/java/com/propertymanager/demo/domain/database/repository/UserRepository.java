package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
