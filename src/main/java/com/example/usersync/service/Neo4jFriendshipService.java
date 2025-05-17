package com.example.usersync.service;

import com.example.usersync.model.Neo4jUserNode;
import com.example.usersync.repository.neo4j.Neo4jUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Neo4jFriendshipService {
    private final Neo4jUserRepository userRepository;

    public Neo4jFriendshipService(Neo4jUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("neo4jTransactionManager")
    public void requestFriendship(Long sourceUserId, Long targetUserId) {
        Neo4jUserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));





    }


}
