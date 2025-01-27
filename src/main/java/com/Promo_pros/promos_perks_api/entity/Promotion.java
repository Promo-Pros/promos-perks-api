package com.Promo_pros.promos_perks_api.entity;

import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import com.Promo_pros.promos_perks_api.roles.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> eligibleRoles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getEligibleRoles() {
        return eligibleRoles;
    }

    public void setEligibleRoles(List<Role> eligibleRoles) {
        this.eligibleRoles = eligibleRoles;
    }
}
