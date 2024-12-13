package com.Promo_pros.promos_perks_api.entity;


import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    private UserStatus status;

}
