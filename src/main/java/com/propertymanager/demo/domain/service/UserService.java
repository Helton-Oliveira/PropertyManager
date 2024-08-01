package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.entity.Tenant;
import com.propertymanager.demo.domain.entity.User;
import com.propertymanager.demo.domain.repository.TenantRepository;
import com.propertymanager.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public Page<UserResponse> fetchAllUsers(Pageable page) {
        return userRepository.findAll(page).map(UserResponse::new);
    }

    public UserResponse getUserDetailsById(Long id) {
        var user = userRepository.getReferenceById(id);
        return new UserResponse(user);
    }

    public UserResponse createUserAccount(UserRequest data) {
        var user = new User(data);
        userRepository.save(user);

        return new UserResponse(user);
    }

    public UserResponse updateUserAccount(UserRequest req, Long id) {
        var user = userRepository.getReferenceById(id);
        user.updateInfo(req);
        userRepository.save(user);

        return new UserResponse(user);
    }

    public void deleteUserAccount(Long id) {
        var user = userRepository.getReferenceById(id);
        user.exclude();
        userRepository.save(user);
    }

    public UserResponse createTenantAccount(UserRequest req) {
        var tenant = new Tenant(req);
        tenantRepository.save(tenant);

        return new UserResponse(tenant);
    }
}
