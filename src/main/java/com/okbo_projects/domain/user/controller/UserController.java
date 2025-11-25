package com.okbo_projects.domain.user.controller;


import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.user.model.request.*;
import com.okbo_projects.domain.user.model.response.UserCreateResponse;
import com.okbo_projects.domain.user.model.response.UserGetMyProfileResponse;
import com.okbo_projects.domain.user.model.response.UserGetOtherProfileResponse;
import com.okbo_projects.domain.user.model.response.UserNicknameUpdateResponse;
import com.okbo_projects.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping
    public ResponseEntity<UserCreateResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.create(request);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 내 정보 조회
    @GetMapping("/myPage")
    public UserGetMyProfileResponse getMyProfile(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser) {
        return userService.getMyProfile(sessionUser);
    }

    // 다른 유저 조회
    @GetMapping("/{nickname}")
    public UserGetOtherProfileResponse getOtherProfile(@PathVariable String nickname) {
        return userService.getOtherProfile(nickname);
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    public ResponseEntity<UserNicknameUpdateResponse> updateNickname(@Valid @RequestBody UserNicknameUpdateRequest request,
                                                                     @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        UserNicknameUpdateResponse response = userService.updateNickname(request, sessionUser);
        return ResponseEntity.ok(response);
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordUpdateRequest request,
                                               @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        userService.updatePassword(request, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody UserDeleteRequest request,
                                       @SessionAttribute(name = "loginUser") SessionUser sessionUser,
                                       HttpSession session) {
        userService.delete(request, sessionUser);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}