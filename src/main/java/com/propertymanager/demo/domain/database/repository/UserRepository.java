package com.propertymanager.demo.domain.database.repository;

import com.propertymanager.demo.domain.database.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM (SELECT 1 FROM User WHERE role = :role LIMIT 1) AS limited_users", nativeQuery = true)
    Long countByRole(String role);

    Long findFirstUserIdByRole(String admin);
}
