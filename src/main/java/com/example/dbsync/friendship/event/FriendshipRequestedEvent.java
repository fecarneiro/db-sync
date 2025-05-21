package com.example.dbsync.friendship.event;

public class FriendshipRequestedEvent {

    private final Long sourceUserId;
    private final Long targetUserId;

    public FriendshipRequestedEvent(Long sourceUserId, Long targetUserId) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }
}
