package com.okbo_projects.domain.follow.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowCountResponse {
    public final long following;
    public final long follower;
}
