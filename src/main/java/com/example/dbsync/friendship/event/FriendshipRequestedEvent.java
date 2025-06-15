package com.example.dbsync.friendship.event;

import lombok.Getter;

@Getter
public class FriendshipRequestedEvent {

    private final Long sourceUserId;
    private final Long targetUserId;

    public FriendshipRequestedEvent(Long sourceUserId, Long targetUserId) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
    }
}
