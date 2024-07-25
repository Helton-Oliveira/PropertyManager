package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.Role;
import com.propertymanager.demo.domain.abstractModels.User;
import com.propertymanager.demo.domain.dtos.AdminRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Admin")
@Table(name = "Admins")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    private Role role;

    public Admin(AdminRequest data) {
        super(data.name(), data.email(), data.password(), data.cpf(), data.phone());
        this.role = data.role();
    }
}
