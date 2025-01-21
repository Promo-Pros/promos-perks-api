package com.Promo_pros.promos_perks_api.util;

import com.Promo_pros.promos_perks_api.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtil {

    //Steps??
//    Create JwtUtil for token generation and validation
//    Implement JwtFilter for intercepting and validating tokens
//    Update SecurityConfig to integrate the filter
//    Add an AuthController for handling login and generating tokens.

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret}")
    private String secret;

    private Key signingKey;

    //Initializes the signing key using the secret
    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //1 hr expiration
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public List<String> getRoles(String token) {
        Claims claims = parseClaims(token);
        return claims.get("roles", List.class);
    }

//may need to get rid of below???
    public boolean validateAccessToken(String token) {
        try {
            Jwts.builder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.builder()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
