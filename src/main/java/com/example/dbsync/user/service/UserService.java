package com.example.dbsync.user.service;

import com.example.dbsync.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private UserNodeService userNodeService;

    // Create User
    @Transactional("postgresTransactionManager")
    public UserEntity createUser(String username, String password) {
        // Creates the user in PostgresSQL and take de generated id
        UserEntity pgUser = userEntityService.createUser(username, password);

        try {
        // Creates the user in Neo4j using the same id and username as PostgresSQL's operations
        userNodeService.createUser(pgUser.getId(), username);
        } catch (Exception e) {
            // If the creation fails in Neo4j, roll back the operation in PostgresSQL throwing an exception
            throw new RuntimeException("Failed to create user in Neo4j. Rolling back PostgresSQL", e);
        }
        return pgUser;
    }

    // Get all Users
    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public List<UserEntity> getAllUsers() {
        return userEntityService.getAllUsers();
    }

    // Get User by ID
    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public Optional<UserEntity> getUserById(Long id) {
        return userEntityService.getUserById(id);
    }

    // Update User
    @Transactional("postgresTransactionManager")
    public UserEntity updateUser(Long id, String username, String password) {

        // First, check if the user exists in PostgresSQL
        if (!userEntityService.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        // Update the user in PostgresSQL
        UserEntity updatedUser = userEntityService.updateUser(id, username, password);

        // Only synchronize username with Neo4j if it was provided
        if (username != null) {
            try {
                userNodeService.updateUser(id, username);
            } catch (Exception e) {
                // If the update fails in Neo4j, roll back the operation in PostgresSQL throwing an exception
                throw new RuntimeException("Failed to update user in Neo4j. Rolling back PostgresSQL", e);
            }
        }
        return updatedUser;
    }

    // Delete User
    @Transactional("postgresTransactionManager")
    public void deleteUser(Long id) {
        // First, check if the user exists in PostgresSQL
        if (!userEntityService.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        // Delete the user in Neo4j
        try {
            userNodeService.deleteUser(id);
            // If successful, delete the user in PostgresSQL
            userEntityService.deleteUser(id);

        } catch (Exception e) {
            // If the deletion fails in Neo4j, roll back the operation in PostgresSQL throwing an exception
            throw new RuntimeException("Failed to delete user in Neo4j. Rolling back PostgresSQL", e);
        }
    }



}
