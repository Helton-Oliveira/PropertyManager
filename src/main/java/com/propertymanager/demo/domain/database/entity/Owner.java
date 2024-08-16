package com.propertymanager.demo.domain.database.entity;

import com.propertymanager.demo.domain.dtos.UserResponse;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity(name = "Owner")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("OWNER")
public class Owner extends User {

    public Owner(UserResponse owner) {
        super(owner);
    }
}
