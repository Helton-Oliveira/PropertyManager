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
    private Boolean active = true;

    /*public User(UserRequest user) {
        this.active = true;
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.cpf = user.getCpf();
        this.phone = user.getPhone();*/
   // }

    public void updateInfo(UserRequest user) {

        if(user.getName() != null) {
            this.name = user.getName();
        }

        if(user.getEmail() != null) {
            this.email = user.getEmail();
        }

        if(user.getCpf() != null) {
            this.cpf = user.getCpf();
        }

        if(user.getPhone() != null) {
            this.phone = user.getPhone();
        }
    }

    public void exclude() {
        this.active = false;
    }
}
