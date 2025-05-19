package com.example.dbsync.user.service;

import com.example.dbsync.user.model.UserNode;
import com.example.dbsync.user.repository.UserNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNodeService {

    private final UserNodeRepository userRepository;

    public UserNodeService(UserNodeRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("neo4jTransactionManager")
    public void createUser(Long id, String username) {
        UserNode user = new UserNode(id, username);
        userRepository.save(user);
    }

    @Transactional(readOnly = true, value = "neo4jTransactionManager")
    public UserNode getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Transactional(readOnly = true, value = "neo4jTransactionManager")
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional("neo4jTransactionManager")
    public void updateUser(Long id, String username) {
        if (username != null) {
            UserNode user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
            user.setUsername(username);
            userRepository.save(user);
        }
    }

    @Transactional("neo4jTransactionManager")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
