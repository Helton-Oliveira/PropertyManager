package com.propertymanager.demo.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.abstractModels.User;
import jakarta.persistence.*;
import lombok.*;


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

    @OneToOne(mappedBy = "tenant")
    private Contract contract;

    public Tenant(String name, String email, String password, String cpf, String phone, Role role) {
        super(name, email, password, cpf, phone, role);
    }

}
