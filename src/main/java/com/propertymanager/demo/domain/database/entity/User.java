package com.propertymanager.demo.domain.database.entity;

import com.propertymanager.demo.domain.dtos.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@DiscriminatorValue("ADMIN")
public class User implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private Boolean active = true;

    @Column(insertable = false, updatable = false)
    private String role;

    public User(UserResponse userResponse) {
        this.name = userResponse.getName();
        this.id = userResponse.getId();
        this.active = true;
        this.phone = userResponse.getPhone();
        this.cpf = userResponse.getCpf();
        this.password = userResponse.getPassword();
        this.email = userResponse.getEmail();
        this.role = userResponse.getRole();
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role.equals("ADMIN")) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role.equals("TENANT")) return List.of(new SimpleGrantedAuthority("ROLE_TENANT"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role.equals("OWNER")) return List.of(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
