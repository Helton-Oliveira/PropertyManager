package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.dtos.UserRequest;
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
    private Boolean active;

    public User(UserRequest user) {
        this.active = true;
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();
        this.cpf = user.cpf();
        this.phone = user.phone();
    }

    public void updateInfo(UserRequest user) {

        if(user.name() != null) {
            this.name = user.name();
        }

        if(user.email() != null) {
            this.email = user.email();
        }

        if(user.cpf() != null) {
            this.cpf = user.cpf();
        }

        if(user.phone() != null) {
            this.phone = user.phone();
        }
    }

    public void exclude() {
        this.active = false;
    }
}
