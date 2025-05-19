package com.example.dbsync.service;

import com.example.dbsync.model.FriendshipRelationship;
import com.example.dbsync.model.Neo4jUserNode;
import com.example.dbsync.repository.neo4j.Neo4jUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipService {
    private final Neo4jUserRepository userRepository;

    public FriendshipService(Neo4jUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Request friendship
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
