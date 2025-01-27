package com.Promo_pros.promos_perks_api.util;

import com.Promo_pros.promos_perks_api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import static com.Promo_pros.promos_perks_api.Constants.SIGNING_KEY;
import static com.Promo_pros.promos_perks_api.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

public class JwtTokenUtil {
    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(authToken);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(authToken));
    }

    public String getUsernameFromToken(String authToken) {
        return getClaimFromToken(authToken, Claims::getSubject);
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail());
    }

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

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
