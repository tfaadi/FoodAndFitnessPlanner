package com.planner.service;

import com.planner.entity.Admin;
import com.planner.entity.User;
import com.planner.repository.AdminRepository;
import com.planner.repository.UserRepository;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    
    //this service function checks if the username previously exists or not 
    //if it does, it returns false, else registers the user, encodes the password, and returns true.
    @Override
    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    //this service function checks if the username previously exists or not 
    //if it does, it returns false, else registers the admin, encodes the password, and returns true.
    @Override
    public boolean registerAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            return false;
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return true;
    }

    //the optional class either has a null value or the object. 
    //to fetch the object we use .get() method 
    //to check if the optional class in null we use .isPresent() method. returns true if not null.
    //we always use .get() methods after checking isPresent(). in case the object is not present(null), it throws NoSuchElementException. 
    @Override
    public Object login(String username, String password, String role) {
        if ("user".equalsIgnoreCase(role)) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                return userOpt.get();
            }
        } else if ("admin".equalsIgnoreCase(role)) {
            Optional<Admin> adminOpt = adminRepository.findByUsername(username);
            if (adminOpt.isPresent() && passwordEncoder.matches(password, adminOpt.get().getPassword())) {
                return adminOpt.get();
            }
        }
        return null; // Invalid login
    }
}

