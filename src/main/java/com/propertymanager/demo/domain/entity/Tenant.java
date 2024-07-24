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
@EqualsAndHashCode(of = "id")
public class Tenant extends User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "tenant")
    private List<Contract> contracts;

    public Tenant(String name, String email, String password, String cpf, String phone, Role role) {
        super(name, email, password, cpf, phone, role);
        this.role = role;
    }

}
