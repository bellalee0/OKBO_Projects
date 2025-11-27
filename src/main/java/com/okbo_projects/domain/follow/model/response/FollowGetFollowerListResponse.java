package com.okbo_projects.domain.follow.model.response;

import com.okbo_projects.domain.follow.model.dto.FollowDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowGetFollowerListResponse {

    private String nickname;

    public static FollowGetFollowerListResponse from(FollowDto followDto) {
        return new FollowGetFollowerListResponse(
                followDto.getFromUser().getNickname()
        );
    }
}
