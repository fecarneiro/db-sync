package com.example.dbsync.controller;

import com.example.dbsync.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // Request friendship
    @PostMapping("/request")
    public ResponseEntity<String> requestFriendship(@RequestParam Long sourceUserId, @RequestParam Long targetUserId) {
        friendshipService.requestFriendship(sourceUserId, targetUserId);
        return ResponseEntity.ok("Friendship request sent.");
    }
}
