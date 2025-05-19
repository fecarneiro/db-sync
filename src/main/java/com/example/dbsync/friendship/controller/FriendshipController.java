package com.example.dbsync.friendship.controller;

import com.example.dbsync.friendship.service.FriendshipService;
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

    // Accept friendship
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendship(@RequestParam Long sourceUserId, @RequestParam Long targetUserId) {
        friendshipService.acceptFriendship(sourceUserId, targetUserId);
        return ResponseEntity.ok("Friendship accepted.");
    }

    // Reject friendship
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendship(@RequestParam Long sourceUserId, @RequestParam Long targetUserId) {
        friendshipService.rejectFriendship(sourceUserId, targetUserId);
        return ResponseEntity.ok("Friendship rejected.");
    }
}
