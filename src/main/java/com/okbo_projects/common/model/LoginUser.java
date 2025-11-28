package com.okbo_projects.common.model;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser {

    private final Long userId;

    public static LoginUser from(UserDto userDto) {
        return new LoginUser(
            userDto.getId()
        );
    }
}