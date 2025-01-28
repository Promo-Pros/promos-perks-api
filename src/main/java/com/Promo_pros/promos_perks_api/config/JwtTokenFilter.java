package com.Promo_pros.promos_perks_api.config;

import com.Promo_pros.promos_perks_api.Constants;
import com.Promo_pros.promos_perks_api.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.Promo_pros.promos_perks_api.Constants.HEADER_STRING;
import static com.Promo_pros.promos_perks_api.Constants.TOKEN_PREFIX;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

//    removed the constructor to allow for use of field injection(@Autowired)
    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {

        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String authToken = null;
        String email = null;

        //Checks if authorization header is valid and extracts JWT
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                email = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException | ExpiredJwtException | SignatureException e) {
                logger.warn("Token validation failed: " + e.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }

}
