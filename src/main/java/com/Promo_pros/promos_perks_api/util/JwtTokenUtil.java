package com.Promo_pros.promos_perks_api.util;

import com.Promo_pros.promos_perks_api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import static com.Promo_pros.promos_perks_api.Constants.SIGNING_KEY;
import static com.Promo_pros.promos_perks_api.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

@Component
public class JwtTokenUtil {

    //Validate JWT Token against username and expiration
    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(authToken);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(authToken));
    }

    //Extract username from JWT token
    public String getUsernameFromToken(String authToken) {
        return getClaimFromToken(authToken, Claims::getSubject);
    }

    //Generate a JWT token for a given user
    public String generateToken(User user) {
        return doGenerateToken(user.getEmail());
    }

    //Core token generation logic
    private String doGenerateToken(String subject) {
        Claims claims = (Claims) Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://promosperks.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    //Retrieve a specific claim from the JWT token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //Retrieve all claims from the JWT token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Check if the token is expired
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Retrieve expiration date from the JWT token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
