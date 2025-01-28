package com.Promo_pros.promos_perks_api.service;

import com.Promo_pros.promos_perks_api.UserDto;
import com.Promo_pros.promos_perks_api.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {

    User save(User user);

    //new
    UserDetails loadUserByEmail(String s) throws UsernameNotFoundException;

    User createUser(User user);

    User getUser(Long id);

    Optional<User> getUserByEmail(String email);

    String loginUser(User user);

}
