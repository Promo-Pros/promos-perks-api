package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import com.Promo_pros.promos_perks_api.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private BCryptUtil bCryptUtil;

    public UserServiceImpl(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, BCryptUtil bCryptUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptUtil = bCryptUtil;
    }

    //may have to add an if statement to assign other roles other than default...
    @Override
    public User createUser(User user) {
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_CUSTOMER")); //set to a default role
        }
        //Hash the password
        String hashedPassword = BCryptUtil.generatedSecurePassword(user.getPassword());
        user.setPassword(hashedPassword);


      User savedUser = userRepository.save(user);

      String token = jwtTokenUtil.generateAccessToken(savedUser);
      // This will be used if we want new users to be automatically logged in
//      savedUser.setToken(token);

      return savedUser;
    }

    @Override
    public User getUser(Long id) {
        System.out.println(id);
        User user = userRepository.findById(id).orElse(null);
        System.out.println(user);
        return user;
    }

//    @Override
//    public User getUserByEmail(String email) {
//        return null;
//    }

    @Override
    public String loginUser(User user) {
        User existingUser =  userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null && BCryptUtil.matchPasswords(user.getPassword(), existingUser.getPassword())) {
            return jwtTokenUtil.generateAccessToken(existingUser);
        }
        return "Invalid Login";

    }


}
