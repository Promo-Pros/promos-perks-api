package com.Promo_pros.promos_perks_api.entity;

import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Enumerated
    private AccountTypes status;

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

    public AccountTypes getStatus() {
        return status;
    }

    public void setStatus(AccountTypes status) {
        this.status = status;
    }
}
