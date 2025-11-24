package com.okbo_projects.domain.follow.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowGetFollowingListResponse {
    private final String nickname;
}
