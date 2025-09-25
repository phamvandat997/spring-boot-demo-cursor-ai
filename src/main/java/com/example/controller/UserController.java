package com.example.controller;

import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing user operations.
 * This controller provides endpoints for user CRUD operations with role-based access control.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Retrieves all users from the database.
     * This endpoint is restricted to users with ADMIN role.
     * 
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Retrieves a user by their unique ID.
     * This endpoint is accessible to authenticated users.
     * 
     * @param id the unique identifier of the user
     * @return ResponseEntity containing the user information or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Retrieves a user by their username.
     * This endpoint is accessible to authenticated users.
     * 
     * @param username the username to search for
     * @return ResponseEntity containing the user information or 404 if not found
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        Optional<UserDto> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Creates a new user.
     * This endpoint is restricted to users with ADMIN role.
     * 
     * @param userDto the user data transfer object containing user information
     * @return ResponseEntity containing the created user information or 400 if creation fails
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * Updates an existing user.
     * This endpoint is accessible to authenticated users.
     * 
     * @param id the unique identifier of the user to update
     * @param userDto the user data transfer object containing updated information
     * @return ResponseEntity containing the updated user information or 400 if update fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * Deletes a user by their unique ID.
     * This endpoint is restricted to users with ADMIN role.
     * 
     * @param id the unique identifier of the user to delete
     * @return ResponseEntity with 200 status if deletion is successful or 400 if deletion fails
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all active users from the database.
     * This endpoint is accessible to authenticated users.
     * 
     * @return ResponseEntity containing a list of active users
     */
    @GetMapping("/active")
    public ResponseEntity<List<UserDto>> getActiveUsers() {
        List<UserDto> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Retrieves all users with a specific role.
     * This endpoint is restricted to users with ADMIN role.
     * 
     * @param role the role to filter users by
     * @return ResponseEntity containing a list of users with the specified role
     */
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        List<UserDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    /**
     * Retrieves the count of active users.
     * This endpoint is restricted to users with ADMIN role.
     * 
     * @return ResponseEntity containing the count of active users
     */
    @GetMapping("/stats/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getActiveUserCount() {
        Long count = userService.getActiveUserCount();
        return ResponseEntity.ok(count);
    }
}
