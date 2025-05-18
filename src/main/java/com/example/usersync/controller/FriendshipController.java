package com.example.usersync.controller;

import com.example.usersync.service.Neo4jFriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {
    private final Neo4jFriendshipService neo4jFriendshipService;

    public FriendshipController(Neo4jFriendshipService neo4jFriendshipService) {
        this.neo4jFriendshipService = neo4jFriendshipService;
    }
    // Request friendship
    @PostMapping
    public ResponseEntity<String> requestFriendship(
            @RequestParam Long sourceUserId,
            @RequestParam Long targetUserId) {
        neo4jFriendshipService.requestFriendship(sourceUserId, targetUserId);
        return ResponseEntity.ok("Friendship requested successfully");
    }
}
