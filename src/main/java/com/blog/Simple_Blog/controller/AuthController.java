package com.blog.Simple_Blog.controller;

import com.blog.Simple_Blog.model.Role;
import com.blog.Simple_Blog.model.User;
import com.blog.Simple_Blog.security.JwtUtil;
import com.blog.Simple_Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userDetails) {
        String username = userDetails.get("username");
        String password = userDetails.get("password");
        String roleString = userDetails.get("role");

        // Pefix ROLE
        if (!roleString.startsWith("ROLE_")) {
            roleString = "ROLE_" + roleString.toUpperCase();
        }

        Set<Role> roles = Set.of(Role.valueOf(roleString));

        userService.registerUser(username, password, roles);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Authentication dan JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        System.out.println("Login request received for: " + username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Fetch user roles dari database
        User user = userService.findUserByUsername(username);
        Set<Role> roles = user.getRoles();

        // Generate JWT Token untuk Role
        String token = jwtUtil.generateToken(username, roles);

        System.out.println("Token generated: " + token);

        return ResponseEntity.ok(Map.of("token", token));
    }
}
