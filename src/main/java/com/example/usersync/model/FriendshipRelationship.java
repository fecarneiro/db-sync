package com.example.usersync.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class FriendshipRelationship {

    @Id @GeneratedValue
    private Long id;

    @

    @TargetNode
    private Neo4jUserNode targetUser;



}
