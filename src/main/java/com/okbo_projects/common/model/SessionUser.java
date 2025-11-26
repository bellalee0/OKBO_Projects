package com.okbo_projects.common.model;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser {

    private final Long userId;
    private final String userEmail;
    private final String userNickname;

    public static SessionUser from(UserDto userDto) {
        return new SessionUser(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getNickname()
        );
    }

    public Object getId() {
        return userId;
    }
}