package com.example.usersync.service;

import com.example.usersync.model.PostgresUserEntity;
import com.example.usersync.repository.postgres.PostgresUserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostgresUserService {

    private final PostgresUserRepository userRepository;

    public PostgresUserService(PostgresUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("postgresTransactionManager")
    public PostgresUserEntity createUser(String username, String password) {
        PostgresUserEntity user = new PostgresUserEntity(username, password);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public List<PostgresUserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public Optional<PostgresUserEntity> getUserById(Long id)  {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public boolean existsById(Long id)  {
        return userRepository.existsById(id);
    }

    @Transactional("postgresTransactionManager")
    public PostgresUserEntity updateUser(Long id, String username, String password) {
        return userRepository.findById(id)
                .map(user -> {
                    if(username != null) {
                        user.setUsername(username);
                    }
                    if(password != null) {
                        user.setPassword(password);
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Transactional("postgresTransactionManager")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
