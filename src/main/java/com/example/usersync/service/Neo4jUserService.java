package com.example.usersync.service;

import com.example.usersync.model.Neo4jUserNode;
import com.example.usersync.repository.neo4j.Neo4jUserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Neo4jUserService {

    @Autowired
    private Neo4jUserRepository userRepository;

    @Transactional("neo4jTransactionManager")
    public void createUser(Long id, String username) {
        Neo4jUserNode user = new Neo4jUserNode(id, username);
        userRepository.save(user);
    }

    @Transactional(readOnly = true, value = "neo4jTransactionManager")
    public Neo4jUserNode getUserById(Long id) {
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
            Neo4jUserNode user = userRepository.findById(id)
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
