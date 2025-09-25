package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for handling authentication operations including user login and registration.
 * This controller provides endpoints for user authentication and account creation.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * Authenticates a user with username and password.
     * Returns a JWT token and user information upon successful authentication.
     * 
     * @param loginRequest the login request containing username and password
     * @return ResponseEntity containing JWT token, token type, and user information
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String, Object> response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Registers a new user account.
     * Creates a new user with the provided information and returns a JWT token.
     * 
     * @param userDto the user data transfer object containing user information
     * @return ResponseEntity containing JWT token, token type, and user information
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDto userDto) {
        try {
            Map<String, Object> response = authService.register(userDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Inner class representing a login request.
     * Contains username and password fields for authentication.
     */
    public static class LoginRequest {
        private String username;
        private String password;
        
        /**
         * Gets the username from the login request.
         * 
         * @return the username
         */
        public String getUsername() {
            return username;
        }
        
        /**
         * Sets the username in the login request.
         * 
         * @param username the username to set
         */
        public void setUsername(String username) {
            this.username = username;
        }
        
        /**
         * Gets the password from the login request.
         * 
         * @return the password
         */
        public String getPassword() {
            return password;
        }
        
        /**
         * Sets the password in the login request.
         * 
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
