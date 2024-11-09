package com.example.mountbank.controller;

import com.example.mountbank.model.User;
import com.example.mountbank.service.UserService;
import com.example.mountbank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid User user) {
        // Call the registerUser method and get the response
        Map<String, Object> response = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword(),
                user.getCoverImage(), user.getBiography(), user.getPhoneNumber());

        // Return the response with appropriate HTTP status
        if ((boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Login and return JWT
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginDetails) {
        // Validate user login
        User user = userService.validateLogin(loginDetails.getPhoneNumber(), loginDetails.getPassword());

        // Generate JWT using phone number
        String jwtToken = jwtUtil.generateToken(user.getPhoneNumber());

        return ResponseEntity.ok("Bearer " + jwtToken); // Return JWT token
    }
}

