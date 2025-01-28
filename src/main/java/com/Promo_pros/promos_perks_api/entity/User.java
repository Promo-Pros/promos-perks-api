package com.Promo_pros.promos_perks_api.entity;


import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long mtn;
    private String email;
    private String password;

    private String status;

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

    public Long getMtn() {
        return mtn;
    }

    public void setMtn(Long mtn) {
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
}