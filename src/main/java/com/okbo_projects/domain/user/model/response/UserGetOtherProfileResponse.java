package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetOtherProfileResponse {
    String nickname;
    String teamName;

    public UserGetOtherProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.teamName = user.getTeam().getTeamName();
    }
}
