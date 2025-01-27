package com.Promo_pros.promos_perks_api.util;

public class AuthToken {
    private String token;
    private String email;

    public AuthToken(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
