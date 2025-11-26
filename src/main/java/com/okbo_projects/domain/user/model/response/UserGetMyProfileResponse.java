package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetMyProfileResponse {
    Long id;
    String nickname;
    String email;
    String teamName;

    public static UserGetMyProfileResponse from(UserDto userDto) {

        return new UserGetMyProfileResponse(
                userDto.getId(),
                userDto.getNickname(),
                userDto.getEmail(),
                userDto.getTeam().getTeamName()
        );
    }
}

