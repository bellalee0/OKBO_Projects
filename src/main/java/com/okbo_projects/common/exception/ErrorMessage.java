package com.okbo_projects.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400
    BAD_REQUEST_NOT_ALLOWED_SELF_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다."),
    BAD_REQUEST_NOT_FOLLOWING_UNFOLLOW(HttpStatus.BAD_REQUEST, "대상을 팔로우 하고 있지 않습니다."),
    BAD_REQUEST_PASSWORD_SAME_AS_CURRENT(HttpStatus.BAD_REQUEST, "현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다."),


    // 401
    UNAUTHORIZED_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED,"로그인이 필요한 서비스 입니다."),
    UNAUTHORIZED_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_WRONG_EMAIL_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호 또는 이메일이 일치하지 않습니다."),


    // 403
    FORBIDDEN_ONLY_WRITER(HttpStatus.FORBIDDEN,"작성자만 이용 가능 합니다."),


    //404
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"존재하지 않는 이메일 입니다"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"존재하지 않는 유저 입니다."),
    NOT_FOUND_FOLLOWING(HttpStatus.NOT_FOUND,"팔로잉 목록이 존재하지 않습니다."),
    NOT_FOUND_FOLLOWER(HttpStatus.NOT_FOUND,"팔로워 목록이 존재하지 않습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND,"해당 게시물을 찾을 수 없습니다."),
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND,"존재하지 않는 구단입니다."),


    // 409
    CONFLICT_USED_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중이거나, 탈퇴한 닉네임 입니다."),
    CONFLICT_USED_EMAIL(HttpStatus.CONFLICT,"이미 사용 중이거나, 탈퇴한 이메일 입니다."),
    CONFLICT_ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우 한 사용자 입니다."),

    ;

    private final HttpStatus status;
    private final String message;
}


