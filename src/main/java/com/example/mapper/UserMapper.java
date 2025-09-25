package com.example.mapper;

import com.example.dto.UserDto;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct mapper interface for converting between User entity and UserDto.
 * This mapper provides type-safe conversion methods with automatic code generation.
 * 
 * @author Spring Boot Demo
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    /**
     * Static instance of the mapper for manual usage.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    /**
     * Converts a User entity to UserDto.
     * This method excludes sensitive information like password for security purposes.
     * 
     * @param user the User entity to convert
     * @return UserDto containing user information without password
     * @throws IllegalArgumentException if user is null
     */
    @Mapping(target = "password", ignore = true) // Exclude password for security
    UserDto toDto(User user);
    
    /**
     * Converts a UserDto to User entity.
     * This method is typically used when creating new users from registration data.
     * Excludes auto-generated fields like id, timestamps, and default values.
     * 
     * @param userDto the UserDto to convert
     * @return User entity ready for persistence
     * @throws IllegalArgumentException if userDto is null
     */
    @Mapping(target = "id", ignore = true) // Exclude id for new entities
    @Mapping(target = "createdAt", ignore = true) // Exclude createdAt for new entities
    @Mapping(target = "updatedAt", ignore = true) // Exclude updatedAt for new entities
    @Mapping(target = "isActive", ignore = true) // Exclude isActive for new entities
    User toEntity(UserDto userDto);
    
    /**
     * Updates an existing User entity with data from UserDto.
     * This method performs partial updates, only updating non-null fields from the DTO.
     * Excludes fields that should not be updated through DTOs like id, timestamps, and password.
     * 
     * @param userDto the UserDto containing updated data
     * @param user the existing User entity to update
     * @throws IllegalArgumentException if userDto is null
     */
    @Mapping(target = "id", ignore = true) // Never update id
    @Mapping(target = "createdAt", ignore = true) // Never update createdAt
    @Mapping(target = "updatedAt", ignore = true) // Never update updatedAt
    @Mapping(target = "password", ignore = true) // Never update password via DTO
    void updateEntity(UserDto userDto, @MappingTarget User user);
    
    /**
     * Converts a list of User entities to a list of UserDto objects.
     * This method applies the same conversion rules as toDto() for each entity.
     * 
     * @param users the list of User entities to convert
     * @return list of UserDto objects, or null if input is null
     */
    List<UserDto> toDtoList(List<User> users);
    
    /**
     * Converts a list of UserDto objects to a list of User entities.
     * This method applies the same conversion rules as toEntity() for each DTO.
     * 
     * @param userDtos the list of UserDto objects to convert
     * @return list of User entities, or null if input is null
     */
    List<User> toEntityList(List<UserDto> userDtos);
}
