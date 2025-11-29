package com.okbo_projects.domain.follow.controller;

import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.follow.model.response.FollowCountResponse;
import com.okbo_projects.domain.follow.model.response.FollowGetFollowerListResponse;
import com.okbo_projects.domain.follow.model.response.FollowGetFollowingListResponse;
import com.okbo_projects.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // Follow 관계 create
    @PostMapping("/{userNickname}")
    public ResponseEntity<Void> createFollow(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @PathVariable String userNickname
    ) {
        followService.createFollow(loginUser, userNickname);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Follow 관계 delete
    @DeleteMapping("/{userNickname}")
    public ResponseEntity<Void> deleteFollow(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @PathVariable String userNickname
    ) {
        followService.deleteFollow(loginUser, userNickname);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Following, Follower 수 count
    @GetMapping("/follow-count")
    public ResponseEntity<FollowCountResponse> countFollow(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @RequestParam(name = "userNickname", required = false) String userNickname
    ) {
        FollowCountResponse result = followService.countFollow(loginUser, userNickname);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Following 유저 리스트 조회
    @GetMapping("/following")
    public ResponseEntity<Page<FollowGetFollowingListResponse>> getFollowingList(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(name = "userNickname", required = false) String userNickname
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<FollowGetFollowingListResponse> result = followService.getFollowingList(loginUser, pageable, userNickname);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Follower 유저 리스트 조회
    @GetMapping("/follower")
    public ResponseEntity<Page<FollowGetFollowerListResponse>> getFollowerList(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(name = "userNickname", required = false) String userNickname
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<FollowGetFollowerListResponse> result = followService.getFollowerList(loginUser, pageable, userNickname);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}