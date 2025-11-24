package com.okbo_projects.domain.follow.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.follow.model.Response.FollowCountResponse;
import com.okbo_projects.domain.follow.model.Response.FollowGetFollowingListResponse;
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

    // Following 유저 조회 (로그인된 유저 기준)
    // TODO : 다른 사람의 팔로잉/팔로워 리스트 조회도 필요할까?
    @GetMapping("/following")
    public ResponseEntity<Page<FollowGetFollowingListResponse>> getFollowingList(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {
        Page<FollowGetFollowingListResponse> result = followService.getFollowingList(sessionUser.getUserId(), page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
