package com.propertymanager.demo.domain.database.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity(name = "Owner")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("OWNER")
public class Owner extends User {

}
