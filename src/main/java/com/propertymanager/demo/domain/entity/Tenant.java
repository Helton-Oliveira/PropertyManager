package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.abstractModels.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "Tenant")
@Table(name = "tenants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Tenant extends User {

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "tenant")
    private List<Contract> contracts;

    public Tenant(String name, String email, String password, String cpf, String phone, Role role) {
        super(name, email, password, cpf, phone);
        this.role = role;
    }

}
