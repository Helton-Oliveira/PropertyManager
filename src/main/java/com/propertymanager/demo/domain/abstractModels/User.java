package com.propertymanager.demo.domain.abstractModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private Role role;

}
