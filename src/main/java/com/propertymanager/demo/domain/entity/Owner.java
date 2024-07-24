package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.abstractModels.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Owner")
@Table(name = "Owners")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Owner extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Property> propertyList;

    public Owner(String name, String email, String password, String cpf, String phone, Role role) {
        super(name, email, password, cpf, phone, role);
        this.role = role;
    }

}
