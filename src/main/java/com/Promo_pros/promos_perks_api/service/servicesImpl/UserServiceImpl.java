package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_USER")); //set to a default role
        }
        //Hash the password
//        String hashedPassword = BCryptUtil.generatedSecurePassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
