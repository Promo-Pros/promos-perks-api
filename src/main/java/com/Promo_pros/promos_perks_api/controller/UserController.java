package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.BCryptUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //POST

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
       return userService.loginUser(user);
    }



    //GET
    @GetMapping("/{id}")
    private User getUser(@PathVariable Long id){
        return  userService.getUser(id);
    }
}
