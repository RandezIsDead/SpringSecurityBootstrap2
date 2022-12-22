package com.randez_trying.SpringSecurityBootstrap.controller;

import com.randez_trying.SpringSecurityBootstrap.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/panel")
    public String adminPanel(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "user";
    }
}
