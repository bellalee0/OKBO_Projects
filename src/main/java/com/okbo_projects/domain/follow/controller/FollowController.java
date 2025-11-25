package com.okbo_projects.domain.follow.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.follow.model.Response.*;
import com.okbo_projects.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // Follow 관계 create (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    @PostMapping("/{userNickname}")
    public ResponseEntity<Void> createFollow(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable String userNickname ) {
        followService.createFollow(sessionUser.getUserId(), userNickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Follow 관계 delete (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    @DeleteMapping("/{userNickname}")
    public ResponseEntity<Void> deleteFollow(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable String userNickname ) {
        followService.deleteFollow(sessionUser.getUserId(), userNickname);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Following, Follower 수 count
    @GetMapping("/follow-count")
    public ResponseEntity<FollowCountResponse> countFollow(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(name = "userNickname", required = false) String userNickname) {
        FollowCountResponse result = followService.countFollow(sessionUser.getUserId(), userNickname);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Following 유저 리스트 조회
    @GetMapping("/following")
    public ResponseEntity<Page<FollowGetFollowingListResponse>> getFollowingList(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "userNickname", required = false) String userNickname ) {
        Page<FollowGetFollowingListResponse> result = followService.getFollowingList(sessionUser.getUserId(), page, size, userNickname);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Follower 유저 리스트 조회
    @GetMapping("/follower")
    public ResponseEntity<Page<FollowGetFollowerListResponse>> getFollowerList(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "userNickname", required = false) String userNickname ) {
        Page<FollowGetFollowerListResponse> result = followService.getFollowerList(sessionUser.getUserId(), page, size, userNickname);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
