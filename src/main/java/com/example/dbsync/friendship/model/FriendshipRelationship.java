package com.example.dbsync.friendship.model;

import com.example.dbsync.user.model.UserNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
public class FriendshipRelationship {
    @RelationshipId
    private Long id;
    @TargetNode
    private UserNode targetUser;
    private FriendshipStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // New friendship relationship constructor
    public FriendshipRelationship(UserNode targetUser) {
        this.targetUser = targetUser;
        this.status = FriendshipStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void accept() {
        if (this.status != FriendshipStatus.ACCEPTED) {
            this.status = FriendshipStatus.ACCEPTED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void reject() {
        if (this.status != FriendshipStatus.REJECTED) {
            this.status = FriendshipStatus.REJECTED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public enum FriendshipStatus {
        PENDING, ACCEPTED, REJECTED
    }
}
