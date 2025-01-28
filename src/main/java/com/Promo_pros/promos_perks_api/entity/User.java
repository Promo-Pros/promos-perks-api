package com.Promo_pros.promos_perks_api.entity;


import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import com.Promo_pros.promos_perks_api.roles.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int mtn;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountTypes status;

    @Enumerated(EnumType.STRING)
    private Role role; //to represent the user role

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMtn() {
        return mtn;
    }

    public void setMtn(int mtn) {
        this.mtn = mtn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountTypes getStatus() {
        return status;
    }

    public void setStatus(AccountTypes status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    //added 5:44 1/27/25
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the role to a GrantedAuthority
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email; // Use email as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Change as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Change as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Change as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Change as needed
    }
}