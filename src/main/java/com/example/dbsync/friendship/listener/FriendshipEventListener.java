package com.example.dbsync.friendship.listener;

import com.example.dbsync.friendship.event.FriendshipRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FriendshipEventListener {

    private static final Logger logger = LoggerFactory.getLogger(FriendshipEventListener.class);

    @EventListener
    public void handleFriendshipRequestedEvent(FriendshipRequestedEvent event) {
        Long sourceUserId = event.getSourceUserId();
        Long targetUserId = event.getTargetUserId();

        // Log the event
        logger.info("[EVENT NOTIFICATION] Friendship requested from user {} to user {}", sourceUserId, targetUserId);
    }
}
