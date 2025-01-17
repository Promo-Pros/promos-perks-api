package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.exception.APIException;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.security.JwtTokenProvider;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

//old method. This one works 100%
//    @Override
//    public String loginUser(User user) {
//        User existingUser =  userRepository.findByEmail(user.getEmail()).orElse(null);
//        if (existingUser != null && BCryptUtil.matchPasswords(user.getPassword(), existingUser.getPassword())) {
//            return "You have logged in";
//        }
//        return "Invalid Login";
//
//    }

    @Override
    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() ->
                new APIException(HttpStatus.NOT_FOUND, "User not found"));

        if (BCryptUtil.matchPasswords(user.getPassword(), existingUser.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    existingUser.getEmail(), null, new ArrayList<>()
            );
            return new JwtTokenProvider().generateToken(authentication);
        }
        throw new APIException(HttpStatus.UNAUTHORIZED, "Invalid login credentials");

    }
}
