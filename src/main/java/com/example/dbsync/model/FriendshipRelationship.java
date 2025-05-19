package com.example.dbsync.model;

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
    private Neo4jUserNode targetUser;
    private FriendshipStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // New friendship relationship constructor
    public FriendshipRelationship(Neo4jUserNode targetUser) {
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
