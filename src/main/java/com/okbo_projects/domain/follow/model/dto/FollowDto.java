package com.okbo_projects.domain.follow.model.dto;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    private Long id;
    private User fromUser;
    private User toUser;

    public static FollowDto from(Follow follow) {
        return new FollowDto(
                follow.getId(),
                follow.getFromUser(),
                follow.getToUser()
        );
    }
}
