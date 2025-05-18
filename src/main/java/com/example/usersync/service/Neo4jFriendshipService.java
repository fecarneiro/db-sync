package com.example.usersync.service;

import com.example.usersync.model.FriendshipRelationship;
import com.example.usersync.model.Neo4jUserNode;
import com.example.usersync.repository.neo4j.Neo4jUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class Neo4jFriendshipService {
    private final Neo4jUserRepository userRepository;

    public Neo4jFriendshipService(Neo4jUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("neo4jTransactionManager")
    public void requestFriendship(Long sourceUserId, Long targetUserId) {
        Neo4jUserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        Neo4jUserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.requestFriendship(targetUser);
        userRepository.save(sourceUser);
    }

    @Transactional("neo4jTransactionManager")
    public void acceptFriendship(Long sourceUserId, Long targetUserId) {
        Neo4jUserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        Neo4jUserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.getPendingFriendships().stream()
                .filter(f -> f.getTargetUser().getId().equals(targetUserId))
                .findFirst()
                .ifPresent(FriendshipRelationship::accept);

        userRepository.save(sourceUser);
    }

    @Transactional("neo4jTransactionManager")
    public void rejectFriendship(Long sourceUserId, Long targetUserId) {
        Neo4jUserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        Neo4jUserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.getPendingFriendships().stream()
                .filter(f -> f.getTargetUser().getId().equals(targetUserId))
                .findFirst()
                .ifPresent(FriendshipRelationship::reject);

        userRepository.save(sourceUser);
    }
}
