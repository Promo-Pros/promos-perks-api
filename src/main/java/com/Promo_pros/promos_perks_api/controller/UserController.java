package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Login user and return token
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.loginUser(user);  // Return JWT token
    }

    // Register a new user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user);  // Register the user
    }
}

