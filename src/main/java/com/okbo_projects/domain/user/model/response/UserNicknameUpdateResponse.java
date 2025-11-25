package com.okbo_projects.domain.user.model.response;

import com.okbo_projects.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameUpdateResponse {
    private Long id;
    private String nickname;

    public UserNicknameUpdateResponse(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
