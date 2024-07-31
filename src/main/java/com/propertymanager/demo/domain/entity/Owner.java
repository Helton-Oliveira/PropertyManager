package com.propertymanager.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Owner")
@Table(name = "Owners")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Owner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "owner")
    private List<Property> propertyList;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean active;
    private String name;
    private String cpf;
    private String email;
    private String phone;
}
