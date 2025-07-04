package com.planner.service;

import com.planner.entity.User;
import com.planner.entity.Admin;

public interface AuthService {
    boolean registerUser(User user);
    boolean registerAdmin(Admin admin);
    Object login(String username, String password, String role); // Returns User/Admin or null
}

