package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import com.Promo_pros.promos_perks_api.util.JwtTokenUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final BCryptUtil bCryptUtil;

    public UserServiceImpl(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, BCryptUtil bCryptUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptUtil = bCryptUtil;
    }

    @Override
    public User createUser(User user) {
        String hashedPassword = BCryptUtil.generatedSecurePassword(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null && BCryptUtil.matchPasswords(user.getPassword(), existingUser.getPassword())) {
            return jwtTokenUtil.generateAccessToken(existingUser);
        }
        return "Invalid Login";
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database using their email
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Use the status field directly for authorities
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())  // The email is used as the username
                .password(user.getPassword())  // The hashed password from the user
                .authorities(getAuthorities(user.getStatus()))  // Use status for authorities
                .build();
    }

    private List<GrantedAuthority> getAuthorities(AccountTypes status) {
        // Return the status as a GrantedAuthority without "ROLE_" prefix
        return List.of(new SimpleGrantedAuthority(status.name()));
    }
}
