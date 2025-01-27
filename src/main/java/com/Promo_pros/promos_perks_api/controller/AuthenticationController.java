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

    @PostMapping("/generate-token")
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        try{
            //Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

            //Fetch user details and generate token
            User user = userService.getUserByEmail(loginUser.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtTokenUtil.generateToken(user);

            return new ApiResponse<>(200, "success", new AuthToken(token, user.getEmail()));

        } catch (AuthenticationException ex) {
            return new ApiResponse<>(401, "Invalid email or password", null);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(404, ex.getMessage(), null);
        }

    }
}
