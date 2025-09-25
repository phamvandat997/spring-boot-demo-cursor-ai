package com.example.mapper;

import com.example.dto.UserDto;
import com.example.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-25T22:24:54+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setRole( user.getRole() );
        userDto.setIsActive( user.getIsActive() );
        userDto.setCreatedAt( user.getCreatedAt() );
        userDto.setUpdatedAt( user.getUpdatedAt() );

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDto.getUsername() );
        user.email( userDto.getEmail() );
        user.password( userDto.getPassword() );
        user.firstName( userDto.getFirstName() );
        user.lastName( userDto.getLastName() );
        user.role( userDto.getRole() );

        return user.build();
    }

    @Override
    public void updateEntity(UserDto userDto, User user) {
        if ( userDto == null ) {
            return;
        }

        if ( userDto.getUsername() != null ) {
            user.setUsername( userDto.getUsername() );
        }
        if ( userDto.getEmail() != null ) {
            user.setEmail( userDto.getEmail() );
        }
        if ( userDto.getFirstName() != null ) {
            user.setFirstName( userDto.getFirstName() );
        }
        if ( userDto.getLastName() != null ) {
            user.setLastName( userDto.getLastName() );
        }
        if ( userDto.getRole() != null ) {
            user.setRole( userDto.getRole() );
        }
        if ( userDto.getIsActive() != null ) {
            user.setIsActive( userDto.getIsActive() );
        }
    }

    @Override
    public List<UserDto> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserDto> userDtos) {
        if ( userDtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtos.size() );
        for ( UserDto userDto : userDtos ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }
}
