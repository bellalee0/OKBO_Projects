package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameUpdateResponse {
    private Long id;
    private String nickname;

    public static UserNicknameUpdateResponse from(UserDto userDto) {
        return new UserNicknameUpdateResponse(
                userDto.getId(),
                userDto.getNickname()
        );
    }
}
