package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.dtos.TenantResponse;
import com.propertymanager.demo.domain.dtos.UserRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Entity(name = "Tenant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("TENANT")
public class Tenant extends User {

    @OneToMany(mappedBy = "tenant")
    private List<Contract> contracts;

    public Tenant(UserRequest user) {
        this.setActive(true);
        this.setName(user.name());
        this.setEmail(user.email());
        this.setPassword(user.password());
        this.setCpf(user.cpf());
        this.setPhone(user.phone());
    }
}
