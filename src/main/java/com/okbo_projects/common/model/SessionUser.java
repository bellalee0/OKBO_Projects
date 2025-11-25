package com.okbo_projects.common.model;

import lombok.Getter;

@Getter
public class SessionUser {

    private final Long userId;
    private final String userEmail;
    private final String userNickname;

    public SessionUser(Long userId, String userEmail, String userNickname) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }

    public Object getId() {
        return userId;
    }
}