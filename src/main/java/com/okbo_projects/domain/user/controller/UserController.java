package com.okbo_projects.domain.user.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.user.model.request.*;
import com.okbo_projects.domain.user.model.response.UserCreateResponse;
import com.okbo_projects.domain.user.model.response.UserGetMyProfileResponse;
import com.okbo_projects.domain.user.model.response.UserGetOtherProfileResponse;
import com.okbo_projects.domain.user.model.response.UserNicknameUpdateResponse;
import com.okbo_projects.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성 (회원가입)
    @PostMapping
    public ResponseEntity<UserCreateResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String createdToken = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(createdToken);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }

    // 내 정보 조회
    @GetMapping("/myPage")
    public ResponseEntity<UserGetMyProfileResponse> getMyProfile(@RequestAttribute(name = "loginUser") SessionUser sessionUser) {
        UserGetMyProfileResponse response = userService.getMyProfile(sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 다른 유저 조회
    @GetMapping("/{nickname}")
    public ResponseEntity<UserGetOtherProfileResponse> getOtherProfile(@PathVariable String nickname) {
        UserGetOtherProfileResponse response = userService.getOtherProfile(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    public ResponseEntity<UserNicknameUpdateResponse> updateNickname(
            @Valid @RequestBody UserNicknameUpdateRequest request,
            @RequestAttribute(name = "loginUser") SessionUser sessionUser) {
        UserNicknameUpdateResponse response = userService.updateNickname(request, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UserPasswordUpdateRequest request,
            @RequestAttribute(name = "loginUser") SessionUser sessionUser) {
        userService.updatePassword(request, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 유저 삭제 (회원 탈퇴)
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Valid @RequestBody UserDeleteRequest request,
            @RequestAttribute(name = "loginUser") SessionUser sessionUser) {
        userService.delete(request, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}