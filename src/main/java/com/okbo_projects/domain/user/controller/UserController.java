package com.okbo_projects.domain.user.controller;

import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.user.model.request.LoginRequest;
import com.okbo_projects.domain.user.model.request.UserCreateRequest;
import com.okbo_projects.domain.user.model.request.UserDeleteRequest;
import com.okbo_projects.domain.user.model.request.UserNicknameUpdateRequest;
import com.okbo_projects.domain.user.model.request.UserPasswordUpdateRequest;
import com.okbo_projects.domain.user.model.response.UserCreateResponse;
import com.okbo_projects.domain.user.model.response.UserGetMyProfileResponse;
import com.okbo_projects.domain.user.model.response.UserGetOtherProfileResponse;
import com.okbo_projects.domain.user.model.response.UserNicknameUpdateResponse;
import com.okbo_projects.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    // 유저 생성 (회원가입)
    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(
        @Valid @RequestBody UserCreateRequest request) {

        UserCreateResponse response = userService.createUser(request);

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
    @GetMapping("/my-page")
    public ResponseEntity<UserGetMyProfileResponse> getMyProfile(
        @RequestAttribute(name = "loginUser") LoginUser loginUser) {

        UserGetMyProfileResponse response = userService.getMyProfile(loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 다른 유저 조회
    @GetMapping("/{nickname}")
    public ResponseEntity<UserGetOtherProfileResponse> getOtherUser(@PathVariable String nickname) {

        UserGetOtherProfileResponse response = userService.getOtherUser(nickname);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    public ResponseEntity<UserNicknameUpdateResponse> updateUserNickname(
        @Valid @RequestBody UserNicknameUpdateRequest request,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        UserNicknameUpdateResponse response = userService.updateUserNickname(request, loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<Void> updateUserPassword(
        @Valid @RequestBody UserPasswordUpdateRequest request,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        userService.updateUserPassword(request, loginUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 유저 삭제 (회원 탈퇴)
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
        @Valid @RequestBody UserDeleteRequest request,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        userService.deleteUser(request, loginUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}