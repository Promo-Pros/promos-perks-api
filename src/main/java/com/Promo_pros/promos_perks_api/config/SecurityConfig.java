package com.Promo_pros.promos_perks_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/user/**").permitAll()// Public endpoint
                        .requestMatchers("/promotions/admin/**").hasRole("ADMIN") //Restricted to admin
                        .requestMatchers("/promotions/veteran/**").hasRole("VETERAN") //Restricted to veterans
                        .requestMatchers("/promotions/employee/**").hasRole("EMPLOYEE") //Restricted to employees
                        .requestMatchers("/promotions/**").hasAnyRole("CUSTOMER", "EMPLOYEE", "VETERAN") //General access
                        .anyRequest().authenticated() // All other endpoints require authentication
                )

        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    //this may need to change...because we are not using in memory db
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder().username("admin@admin.com").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .build();

        UserDetails user = User.builder().username("user@user.com").password(passwordEncoder().encode("user")).roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
