package com.example.usersync.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("User")
@Getter
@Setter
@NoArgsConstructor
public class Neo4jUserNode {
    @Id
    private Long id;
    private String username;

    @Relationship(type = "FRIENDSHIP", direction = Relationship.Direction.OUTGOING)
    private List<FriendshipRelationship> friendships = new ArrayList<>();

    public Neo4jUserNode(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public void requestFriendship(Neo4jUserNode targetUser) {
        FriendshipRelationship friendship = new FriendshipRelationship(targetUser);
        this.friendships.add(friendship);
    }

    public List<FriendshipRelationship> getPendingFriendships() {
        return friendships.stream()
                .filter(f -> f.getStatus() == FriendshipRelationship.FriendshipStatus.PENDING)
                .toList();
    }

    public List<FriendshipRelationship> getAcceptedFriendships() {
        return friendships.stream()
                .filter(f -> f.getStatus() == FriendshipRelationship.FriendshipStatus.ACCEPTED)
                .toList();
    }

    public List<FriendshipRelationship> getRejectedFriendships() {
        return friendships.stream()
                .filter(f -> f.getStatus() == FriendshipRelationship.FriendshipStatus.REJECTED)
                .toList();
    }
}
