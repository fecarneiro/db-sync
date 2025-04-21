package com.example.neo4jdemo.service;

import com.example.neo4jdemo.dto.UserDTO;
import com.example.neo4jdemo.model.User;
import com.example.neo4jdemo.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(UserDTO userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user)
    }
}