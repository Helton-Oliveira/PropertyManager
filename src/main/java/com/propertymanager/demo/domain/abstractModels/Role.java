package com.propertymanager.demo.domain.abstractModels;

public enum Role {
    ADMIN("admin"),
    TENANT("inquilino"),
    OWNER("proprietario");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
