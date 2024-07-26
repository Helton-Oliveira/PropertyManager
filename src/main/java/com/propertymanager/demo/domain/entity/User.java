package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.endereco.Addres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Tenant tenant;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Owner owner;

    public User(UserRequest user) {
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();
        this.cpf = user.cpf();
        this.phone = user.phone();
        this.role = user.role();
    }
}
