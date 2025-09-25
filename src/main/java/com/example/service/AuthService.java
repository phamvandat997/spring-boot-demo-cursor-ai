package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for handling authentication operations including user login and registration.
 * This service manages JWT token generation and user authentication processes.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * Authenticates a user with username and password, then generates a JWT token.
     * Sets the authentication context and returns user information with the token.
     * 
     * @param username the username for authentication
     * @param password the password for authentication
     * @return Map containing JWT token, token type, and user information
     * @throws RuntimeException if authentication fails or user is not found
     */
    public Map<String, Object> login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            String token = jwtUtil.generateToken(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");
            response.put("user", userMapper.toDto(user));
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
    
    /**
     * Registers a new user with the provided user data and generates a JWT token.
     * Validates that username and email are unique before creating the user.
     * Password is automatically encoded using BCrypt before saving.
     * 
     * @param userDto the user data transfer object containing user information
     * @return Map containing JWT token, token type, and user information
     * @throws RuntimeException if username or email already exists
     */
    public Map<String, Object> register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode password
        
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("user", userMapper.toDto(savedUser));
        
        return response;
    }
    
}
