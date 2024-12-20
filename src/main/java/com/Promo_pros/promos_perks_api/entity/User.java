package com.Promo_pros.promos_perks_api.entity;


import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int mtn;
    private String email;
    private String password;
    @Enumerated
    private AccountTypes status;

}
