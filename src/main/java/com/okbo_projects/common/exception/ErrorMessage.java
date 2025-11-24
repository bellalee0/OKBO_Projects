package com.okbo_projects.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 401
    UNAUTHORIZED_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED,"로그인이 필요한 서비스 입니다."),
    UNAUTHORIZED_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),


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
    CONFLICT_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),
    CONFLICT_EMAIL(HttpStatus.CONFLICT,"중복된 이메일 입니다.");


    private final HttpStatus status;
    private final String message;
}


