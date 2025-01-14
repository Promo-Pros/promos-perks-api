package com.Promo_pros.promos_perks_api.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "index";
    }

//    @GetMapping("/home")
//    public String home() {
//        return "Welcome to home page";
//    }
}
