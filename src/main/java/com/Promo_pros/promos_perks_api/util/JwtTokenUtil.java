package com.Promo_pros.promos_perks_api.util;


import com.Promo_pros.promos_perks_api.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenUtil {


    @Value("${jwt.expiration}")
    private long expiration;


    @Value("${jwt.secret}")
    private String secret;


    /**
     * Generates a JWT token for the given user.
     *
     * @param user The user object for whom the token is generated.
     * @return A signed JWT token.
     */
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // User's email as the subject
                .claim("roles", user.getRoles()) // Store roles as claims
                .setIssuedAt(new Date()) // Token issue date
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Expiration time
                .signWith(SignatureAlgorithm.HS512, secret) // Sign with HS512 algorithm and secret
                .compact();
    }


    /**
     * Extracts roles from the given JWT token.
     *
     * @param token The JWT token.
     * @return A list of roles.
     */
    public List<String> getRoles(String token) {
        Claims claims = parseClaims(token);
        return Arrays.asList(claims.get("roles", String.class).split(","));
    }


    /**
     * Validates the given JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid; false otherwise.
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true; // Token is valid
        } catch (ExpiredJwtException ex) {
            System.err.println("JWT expired: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.err.println("Invalid JWT: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.err.println("Invalid JWT signature: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string is empty: " + ex.getMessage());
        }
        return false; // Token is invalid
    }


    /**
     * Extracts the subject (e.g., email) from the token.
     *
     * @param token The JWT token.
     * @return The subject (e.g., email).
     */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }


    /**
     * Parses claims from the given token.
     *
     * @param token The JWT token.
     * @return Claims extracted from the token.
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Use the secret to parse the token
                .parseClaimsJws(token)
                .getBody();
    }
}

