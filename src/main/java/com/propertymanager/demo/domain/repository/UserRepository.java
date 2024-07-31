package com.propertymanager.demo.domain.repository;

import com.propertymanager.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id =:id AND u.role = 'TENANT'")
    User searchForTenantById(Long id);

}
