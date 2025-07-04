package com.planner.controller;

import com.planner.entity.User;
import com.planner.dto.LoginDto;
import com.planner.dto.RegisterDto;
import com.planner.entity.Admin;
import com.planner.service.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    
    
    //if we click on localhost:8080/ it automatically redirects you to the login page 
    @GetMapping("/")
    public String home() {
    	return "redirect:/login";
    }

    // Show login page
    //we create a model attribute and name it loginRequest, the value it holds is the empty login DTO object. 
    //in the html file, we map these attributes
    //the Model interface is used in controller methods to send data(empty loginDto object) from controller to the views(auth/login.html)
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginDto());
        return "auth/login";
    }

    // Show register page
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterDto());
        return "auth/register";
    }

    // Process registration
    @PostMapping("/register")
    public String processRegister(@ModelAttribute RegisterDto registerDto, Model model) {
        boolean success = false;

        if ("user".equalsIgnoreCase(registerDto.getRole())) {
            User user = new User(registerDto.getFullName(), registerDto.getEmail(),
                    registerDto.getUsername(), registerDto.getPassword());
            success = authService.registerUser(user);
        } else if ("admin".equalsIgnoreCase(registerDto.getRole())) {
            Admin admin = new Admin(registerDto.getFullName(), registerDto.getEmail(),
                    registerDto.getUsername(), registerDto.getPassword());
            success = authService.registerAdmin(admin);
        }

        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("registerRequest", new RegisterDto());            
            return "auth/register";
        }
    }

    // Process login
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginDto loginDto,
                               HttpSession session,
                               Model model) {
        Object loggedIn = authService.login(loginDto.getUsername(), loginDto.getPassword(), loginDto.getRole());

        if (loggedIn == null) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("loginRequest", new LoginDto());
            return "auth/login";
        }


        session.setAttribute("user", loggedIn);
        session.setAttribute("role", loginDto.getRole().toLowerCase());

        if ("user".equalsIgnoreCase(loginDto.getRole())) {
            return "redirect:/user/dashboard";
        } else {
            return "redirect:/admin/dashboard";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

