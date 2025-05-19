package com.example.dbsync.user.service;

import com.example.dbsync.user.model.UserEntity;
import com.example.dbsync.user.repository.UserEntityRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService {

    private final UserEntityRepository userRepository;

    public UserEntityService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("postgresTransactionManager")
    public UserEntity createUser(String username, String password) {
        UserEntity user = new UserEntity(username, password);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public Optional<UserEntity> getUserById(Long id)  {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public boolean existsById(Long id)  {
        return userRepository.existsById(id);
    }

    @Transactional("postgresTransactionManager")
    public UserEntity updateUser(Long id, String username, String password) {
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
