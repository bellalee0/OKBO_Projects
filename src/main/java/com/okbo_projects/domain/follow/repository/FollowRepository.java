package com.okbo_projects.domain.follow.repository;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.okbo_projects.common.exception.ErrorMessage.BAD_REQUEST_NOT_FOLLOWING_UNFOLLOW;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // fromUser, toUser 2가지 기준으로 조회(Optional로 반환)
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
    default Follow findFollowByFromUserAndToUser(User fromUser, User toUser) {
        return findByFromUserAndToUser(fromUser, toUser)
                .orElseThrow(() -> new CustomException(BAD_REQUEST_NOT_FOLLOWING_UNFOLLOW));
    }

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

    // User가 FromUser인 경우 존재 여부 확인(존재 시 true 반환)
    boolean existsByFromUser(User user);

    // User가 ToUser인 경우 존재 여부 확인(존재 시 true 반환)
    boolean existsByToUser(User user);
}
