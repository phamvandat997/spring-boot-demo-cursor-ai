package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for public endpoints that do not require authentication.
 * This controller provides endpoints for health checks and application information.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {
    
    /**
     * Health check endpoint to verify the application is running.
     * Returns the current status and timestamp of the application.
     * 
     * @return ResponseEntity containing the health status and timestamp
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "message", "Spring Boot application is running!",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
    
    /**
     * Information endpoint that provides details about the application.
     * Returns application name, version, description, and list of features.
     * 
     * @return ResponseEntity containing application information and features
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
            "application", "Spring Boot Demo",
            "version", "1.0.0",
            "description", "Spring Boot application with JWT authentication and database integration",
            "features", new String[]{
                "JWT Authentication",
                "Spring Security",
                "H2 Database",
                "RESTful APIs",
                "User Management"
            }
        ));
    }
}
