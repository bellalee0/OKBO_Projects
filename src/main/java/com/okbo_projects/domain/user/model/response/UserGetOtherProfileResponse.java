package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetOtherProfileResponse {

    private String nickname;
    private String teamName;

    public static UserGetOtherProfileResponse from(UserDto userDto) {
        return new UserGetOtherProfileResponse(
                userDto.getNickname(),
                userDto.getTeam().getTeamName()
        );
    }
}
