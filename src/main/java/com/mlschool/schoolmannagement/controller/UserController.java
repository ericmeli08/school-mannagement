package com.mlschool.schoolmannagement.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlschool.schoolmannagement.model.user.User;
import com.mlschool.schoolmannagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping(path = "/sign_up")
    public String postSignUp(Model model,@ModelAttribute User user)
    {
        if (userRepository.findByUsername(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists !");
            return "/user/sign_up";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("message", "Registered Successfuly!");

        return "sign_up";
    }
    
    @GetMapping(path = "/sign_up")
    public String signUp(Model model)
    {
        model.addAttribute("user",new User());
        return "/user/sign_up";
    }

    
    @GetMapping(path = "/login")
    public String login(Model model)
    {
        return "/user/sign_in";
    }

}
