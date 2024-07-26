package com.propertymanager.demo.domain.dtos;

import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.entity.User;

public record UserResponse(
        Long id,
        String name,
        String cpf,
        String phone,
        Role role) {

    public UserResponse(User admin) {
        this(admin.getId(), admin.getName(), admin.getCpf(), admin.getPhone(), admin.getRole());
    }

}
