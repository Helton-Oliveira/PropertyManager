package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.entity.User;
import com.propertymanager.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserResponse> getAllUsers(Pageable page) {
        return userRepository.findAll(page).map(UserResponse::new);
    }

    public UserResponse getOneUser(Long id) {
        var user = userRepository.getReferenceById(id);
        return new UserResponse(user);
    }

    public UserResponse created(UserRequest data) {
        var user = new User(data);
        userRepository.save(user);

        return new UserResponse(user);
    }

    public UserResponse update(UserRequest req, Long id) {
        var user = userRepository.getReferenceById(id);
        user.updateInfo(req);
        userRepository.save(user);

        return new UserResponse(user);
    }

    public void delete(Long id) {
        var user = userRepository.getReferenceById(id);
        user.exclude();
        userRepository.save(user);
    }
}
