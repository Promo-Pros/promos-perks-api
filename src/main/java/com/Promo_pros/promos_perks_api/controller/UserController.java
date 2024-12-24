package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService) {

        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //POST mapping for creating a User

    @PostMapping
    public User createUser(@RequestBody User user){

        return userService.createUser(user);
    }
    //Post mapping for logging a User in

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User existingUser = userService.getUserByEmail(user.getEmail());

        if (existingUser != null && bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return "You have logged in!";
        } else {
            return "Invalid Login";
        }
    }
    // now how do we ensure that only people that are logged in are able to see the promotions?
    //GET
    @GetMapping("/{id}")
    private User getUser(@PathVariable Long id){

        return  userService.getUser(id);
    }
}
