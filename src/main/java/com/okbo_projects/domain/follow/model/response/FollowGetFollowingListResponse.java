package com.okbo_projects.domain.follow.model.response;

import com.okbo_projects.domain.follow.model.dto.FollowDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowGetFollowingListResponse {
    private String nickname;

    public static FollowGetFollowingListResponse from(FollowDto followDto) {
        return new FollowGetFollowingListResponse(
                followDto.getToUser().getNickname()
        );
    }
}
