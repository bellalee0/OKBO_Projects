package com.okbo_projects.domain.follow.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowCountResponse {
    public final Long following;
    public final Long follower;
}
