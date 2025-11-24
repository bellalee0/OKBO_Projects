package com.okbo_projects.domain.follow.controller;

import com.okbo_projects.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // Follow 관계 create (following: 로그인한 유저 / follower: Path Variable로 입력받은 유저)
    @PostMapping("/{userId}/{userNickname}")
    public ResponseEntity<Void> createFollow(
//            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long userId,
            @PathVariable String userNickname ) {
//        followService.createFollow(sessionUser.getId(), userNickname);
        followService.createFollow(userId, userNickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
