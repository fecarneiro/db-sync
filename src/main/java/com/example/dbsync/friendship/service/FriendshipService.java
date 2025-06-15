package com.example.dbsync.friendship.service;

import com.example.dbsync.friendship.event.FriendshipRequestedEvent;
import com.example.dbsync.friendship.model.FriendshipRelationship;
import com.example.dbsync.user.model.UserNode;
import com.example.dbsync.user.repository.UserNodeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipService {

    private final UserNodeRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FriendshipService(UserNodeRepository userNodeRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userNodeRepository;
        this.eventPublisher = eventPublisher;
    }

    // Request friendship
    @Transactional("neo4jTransactionManager")
    public void requestFriendship(Long sourceUserId, Long targetUserId) {
        UserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        UserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.requestFriendship(targetUser);
        userRepository.save(sourceUser);

        // Create event object
        FriendshipRequestedEvent event = new FriendshipRequestedEvent(sourceUserId, targetUserId);
        // Publish event
        eventPublisher.publishEvent(event);
    }

    // Accept friendship
    @Transactional("neo4jTransactionManager")
    public void acceptFriendship(Long sourceUserId, Long targetUserId) {
        UserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        UserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.getPendingFriendships().stream()
                .filter(f -> f.getTargetUser().getId().equals(targetUserId))
                .findFirst()
                .ifPresent(FriendshipRelationship::accept);

        userRepository.save(sourceUser);
    }

    // Reject friendship
    @Transactional("neo4jTransactionManager")
    public void rejectFriendship(Long sourceUserId, Long targetUserId) {
        UserNode sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new RuntimeException("Source user not found"));

        UserNode targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        sourceUser.getPendingFriendships().stream()
                .filter(f -> f.getTargetUser().getId().equals(targetUserId))
                .findFirst()
                .ifPresent(FriendshipRelationship::reject);

        userRepository.save(sourceUser);
    }
}


