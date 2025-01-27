package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.UserDto;
import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDto user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setMtn(user.getMtn());
        return userRepository.save(newUser);
    }

    //new
    @Override
    public UserDetails loadUserByEmail(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),getAuthority());
    }

    private Collection<? extends GrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public User createUser(User user) {
        //Hash the password
        String hashedPassword = BCryptUtil.generatedSecurePassword(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public String loginUser(User user) {
        User existingUser =  userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null && BCryptUtil.matchPasswords(user.getPassword(), existingUser.getPassword())) {
            return "You have logged in";
        }
        return "Invalid Login";

    }


}
