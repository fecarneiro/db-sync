package com.example.neo4jdemo.mapper;

import com.example.neo4jdemo.dto.UserDTO;
import com.example.neo4jdemo.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
