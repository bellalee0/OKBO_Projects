package com.okbo_projects.domain.follow.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowCountResponse {

    public Long following;
    public Long follower;

    public static FollowCountResponse from(Long following, Long follower) {
        return new FollowCountResponse(following, follower);
    }
}
