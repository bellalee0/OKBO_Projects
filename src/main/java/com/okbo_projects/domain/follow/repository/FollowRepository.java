package com.okbo_projects.domain.follow.repository;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // fromUser, toUser 2가지 기준으로 조회(Optional로 반환)
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    // fromUser, toUser의 Follow 존재 여부 확인(존재 시 true 반환)
    boolean existsByFromUserAndToUser(User fromUser, User toUser);

    // User의 Following 수 카운트
    long countByFromUser(User user);

    // User의 Follower 수 카운트
    long countByToUser(User user);

    // user가 FromUser인 경우 조회(페이지네이션 적용)
    Page<Follow> findByFromUser(User user, Pageable pageable);

    // user가 ToUser인 경우 조회(페이지네이션 적용)
    Page<Follow> findByToUser(User user, Pageable pageable);
}
