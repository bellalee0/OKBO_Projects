package com.okbo_projects.domain.follow.repository;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // following, follower 2가지 기준으로 조회(Optional로 반환)
    Optional<Follow> findByFollowingAndFollower(User following, User follower);
}
