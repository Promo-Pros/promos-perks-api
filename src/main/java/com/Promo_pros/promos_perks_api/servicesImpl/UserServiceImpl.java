package com.Promo_pros.promos_perks_api.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.repository.UserRepository;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import org.springframework.stereotype.Service;

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


}
