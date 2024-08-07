package com.propertymanager.demo.domain.database.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


@Entity(name = "Tenant")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("TENANT")
public class Tenant extends User {

}
