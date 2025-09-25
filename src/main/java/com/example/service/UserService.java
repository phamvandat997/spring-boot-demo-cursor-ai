package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user operations and implementing Spring Security UserDetailsService.
 * This service provides methods for user CRUD operations, authentication, and user management.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * Loads user details by username for Spring Security authentication.
     * This method is required by the UserDetailsService interface.
     * 
     * @param username the username to search for
     * @return UserDetails object containing user information for authentication
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
    
    /**
     * Creates a new user with the provided user data.
     * Validates that username and email are unique before creating the user.
     * Password is automatically encoded using BCrypt before saving.
     * 
     * @param userDto the user data transfer object containing user information
     * @return UserDto containing the created user information (without password)
     * @throws RuntimeException if username or email already exists
     */
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole() != null ? userDto.getRole() : Role.USER);
        
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    /**
     * Retrieves all users from the database.
     * 
     * @return list of UserDto objects containing all users (without passwords)
     */
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
    
    /**
     * Retrieves a user by their unique ID.
     * 
     * @param id the unique identifier of the user
     * @return Optional containing UserDto if found, empty Optional otherwise
     */
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }
    
    /**
     * Retrieves a user by their username.
     * 
     * @param username the username to search for
     * @return Optional containing UserDto if found, empty Optional otherwise
     */
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }
    
    /**
     * Updates an existing user with new information.
     * Validates email uniqueness if email is being changed.
     * Encodes password if a new password is provided.
     * 
     * @param id the unique identifier of the user to update
     * @param userDto the user data transfer object containing updated information
     * @return UserDto containing the updated user information (without password)
     * @throws RuntimeException if user is not found or email already exists
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Check email uniqueness if email is being changed
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
        }
        
        // Update password if provided
        if (userDto.getPassword() != null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        // Use MapStruct to update entity
        userMapper.updateEntity(userDto, user);
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
    
    /**
     * Deletes a user by their unique ID.
     * 
     * @param id the unique identifier of the user to delete
     * @throws RuntimeException if user is not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    /**
     * Retrieves all active users from the database.
     * 
     * @return list of UserDto objects containing active users (without passwords)
     */
    public List<UserDto> getActiveUsers() {
        return userMapper.toDtoList(userRepository.findByIsActiveTrue());
    }
    
    /**
     * Retrieves all users with a specific role.
     * 
     * @param role the role to filter users by
     * @return list of UserDto objects containing users with the specified role (without passwords)
     */
    public List<UserDto> getUsersByRole(Role role) {
        return userMapper.toDtoList(userRepository.findByRole(role));
    }
    
    /**
     * Counts the total number of active users in the database.
     * 
     * @return the count of active users
     */
    public Long getActiveUserCount() {
        return userRepository.countActiveUsers();
    }
}
