package com.example.usersync.service;

import com.example.usersync.model.PostgresUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserSyncService {

    @Autowired
    private PostgresUserService postgresUserService;

    @Autowired
    private Neo4jUserService neo4jUserService;

    // Create User
    @Transactional("postgresTransactionManager")
    public PostgresUserEntity createUser(String username, String password) {
        // Creates the user in PostgresSQL and take de generated id
        PostgresUserEntity pgUser = postgresUserService.createUser(username, password);

        try {
        // Creates the user in Neo4j using the same id and username as PostgresSQL's operations
        neo4jUserService.createUser(pgUser.getId(), username);
        } catch (Exception e) {
            // If the creation fails in Neo4j, roll back the operation in PostgresSQL throwing an exception
            throw new RuntimeException("Failed to create user in Neo4j. Rolling back PostgresSQL", e);
        }
        return pgUser;
    }

    // Get all Users
    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public List<PostgresUserEntity> getAllUsers() {
        return postgresUserService.getAllUsers();
    }

    // Get User by ID
    @Transactional(readOnly = true, value = "postgresTransactionManager")
    public Optional<PostgresUserEntity> getUserById(Long id) {
        return postgresUserService.getUserById(id);
    }

    // Update User
    @Transactional("postgresTransactionManager")
    public PostgresUserEntity updateUser(Long id, String username, String password) {

        // First, check if the user exists in PostgresSQL
        if (!postgresUserService.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        // Update the user in PostgresSQL
        PostgresUserEntity updatedUser = postgresUserService.updateUser(id, username, password);

        // Only synchronize username with Neo4j if it was provided
        if (username != null) {
            try {
                neo4jUserService.updateUser(id, username);
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
        if (!postgresUserService.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        // Delete the user in Neo4j
        try {
            neo4jUserService.deleteUser(id);
            // If successful, delete the user in PostgresSQL
            postgresUserService.deleteUser(id);

        } catch (Exception e) {
            // If the deletion fails in Neo4j, roll back the operation in PostgresSQL throwing an exception
            throw new RuntimeException("Failed to delete user in Neo4j. Rolling back PostgresSQL", e);
        }
    }



}
