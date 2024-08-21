package com.propertymanager.demo.domain.database.entity;

import com.propertymanager.demo.domain.dtos.UserResponse;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Tenant")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("TENANT")
@NoArgsConstructor

public class Tenant extends User {

    public Tenant(UserResponse userResponse) {
        super(userResponse);
    }
}
