package com.example.dbsync.controller;

import com.example.dbsync.model.PostgresUserEntity;
import com.example.dbsync.service.UserSyncService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserSyncService userSyncService;

    public UserController(UserSyncService userSyncService) {
        this.userSyncService = userSyncService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody PostgresUserEntity user) {
        try {
            PostgresUserEntity createdUser = userSyncService.createUser(
                    user.getUsername(),
                    user.getPassword()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user: " + e.getMessage());
        }
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<List<PostgresUserEntity>> getAllUsers() {
        List<PostgresUserEntity> users = userSyncService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<PostgresUserEntity> userOpt = userSyncService.getUserById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody PostgresUserEntity user) {
        try {
            PostgresUserEntity updatedUser = userSyncService.updateUser(
                    id,
                    user.getUsername(),
                    user.getPassword()
            );
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update user: " + e.getMessage());
        }
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userSyncService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }
}