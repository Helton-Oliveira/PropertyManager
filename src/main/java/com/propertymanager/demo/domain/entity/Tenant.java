package com.propertymanager.demo.domain.entity;

import com.propertymanager.demo.domain.abstractModels.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "Tenant")
@Table(name = "tenants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Tenant{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "tenant")
    private List<Contract> contracts;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String cpf;

}
