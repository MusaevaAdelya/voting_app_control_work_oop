package com.example.app1.controllers;

import com.example.app1.dto.RegisterForm;
import com.example.app1.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model){

        return "login_register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterForm registerForm){
        log.info("got request to /register controller");
        userService.register(registerForm);
        return "redirect:/";
    }
}