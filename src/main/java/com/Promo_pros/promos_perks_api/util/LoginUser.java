package com.Promo_pros.promos_perks_api.util;

public class LoginUser {
    private String email;
    private String password;

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
