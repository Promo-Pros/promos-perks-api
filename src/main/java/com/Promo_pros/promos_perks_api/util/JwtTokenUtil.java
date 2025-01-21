package com.Promo_pros.promos_perks_api.util;

import com.Promo_pros.promos_perks_api.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail() + "," + String.join(",", user.getRoles()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //1 hr expiration
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public List<String> getRoles(String token) {
        String subject = getSubject(token);
        return Arrays.asList(subject.split(",")[1].split(","));
    }

//may need to get rid of below???
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
