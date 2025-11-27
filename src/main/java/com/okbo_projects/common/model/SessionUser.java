package com.okbo_projects.common.model;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser {

    private final Long userId;

    public static SessionUser from(UserDto userDto) {
        return new SessionUser(
                userDto.getId()
        );
    }
}