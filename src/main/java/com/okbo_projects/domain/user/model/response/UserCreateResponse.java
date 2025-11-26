package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.domain.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateResponse {
    private Long id;
    private String nickname;
    private String email;
    private String teamName;

    public static UserCreateResponse from(UserDto userDto) {
        return new UserCreateResponse(
                userDto.getId(),
                userDto.getNickname(),
                userDto.getEmail(),
                userDto.getTeam().getTeamName()
        );
    }
}
