package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<User, Long, UserResponse, UserRequest>{

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        super(UserResponse.class, User.class);
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            var entity = userRepository.getReferenceById(id);
            entity.setActive(false);
            userRepository.save(entity);
            return true;
        }
        return false;
    }

}
