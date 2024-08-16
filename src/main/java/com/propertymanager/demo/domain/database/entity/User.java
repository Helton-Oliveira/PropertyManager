package com.propertymanager.demo.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.propertymanager.demo.domain.dtos.UserResponse;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@DiscriminatorValue("ADMIN")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private Boolean active = true;

    public User(UserResponse userResponse) {
        this.name = userResponse.getName();
        this.id = userResponse.getId();
        this.active = true;
        this.phone = userResponse.getPhone();
        this.cpf = userResponse.getCpf();
        this.password = userResponse.getPassword();
        this.email = userResponse.getEmail();
    }
}
