package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.common.entity.User;
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

    public UserGetMyProfileResponse(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.teamName = user.getTeam().getTeamName();
    }
}

