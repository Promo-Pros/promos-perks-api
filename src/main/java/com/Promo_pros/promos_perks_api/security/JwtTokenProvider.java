package com.Promo_pros.promos_perks_api.security;

import com.Promo_pros.promos_perks_api.exception.APIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //Generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;

    }

    private Key key () {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    //validate token
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return true;

        } catch(MalformedJwtException error){
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch(ExpiredJwtException error) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Expired token");
        } catch(UnsupportedJwtException error) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported token");
        } catch(IllegalArgumentException error) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid token - String is empty");
        }
    }

}
