package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.User;
import com.Promo_pros.promos_perks_api.service.UserService;
import com.Promo_pros.promos_perks_api.util.ApiResponse;
import com.Promo_pros.promos_perks_api.util.AuthToken;
import com.Promo_pros.promos_perks_api.util.JwtTokenUtil;
import com.Promo_pros.promos_perks_api.util.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final User user = userService.getUser(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getEmail()));
    }
}
