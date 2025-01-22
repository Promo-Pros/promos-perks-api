package com.Promo_pros.promos_perks_api.entity;


import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int mtn;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountTypes status; //Business logic related account type

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>(); //Security related roles

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

    public Set<String> getRoles(){
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}