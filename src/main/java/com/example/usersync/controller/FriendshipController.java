package com.example.usersync.controller;

import com.example.usersync.service.Neo4jFriendshipService;
import org.springframework.web.bind.annotation.PostMapping;

public class FriendshipController {

    private final Neo4jFriendshipService neo4jFriendshipService;

    public FriendshipController(Neo4jFriendshipService neo4jFriendshipService) {
        this.neo4jFriendshipService = neo4jFriendshipService;
    }

    // Request friendship
    @PostMapping("/{sourceUserId}/friendship/{targetUserId}/request")
    public


    // Accept friendship
    @PostMapping("/{sourceUserId}/friendship/{targetUserId}/accept")




    // Request
    @PostMapping("/{sourceUserId}/friendship/{targetUserId}/reject")
}
