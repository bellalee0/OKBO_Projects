package com.okbo_projects.domain.follow.service;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import com.okbo_projects.domain.follow.repository.FollowRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // Follow 관계 create (following: 로그인한 유저 / follower: Path Variable로 입력받은 유저)
    public void createFollow(Long userId, String userNickname) {
        User following = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        User follower = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        // TODO : 커스텀 예외로 변경
        if (following.equals(follower)) { throw new IllegalStateException("SAME_USERS"); }
        boolean checkFollowExistence = followRepository.existsByFollowingAndFollower(following, follower);
        // TODO : 커스텀 예외로 변경
        if (checkFollowExistence) { throw new IllegalStateException("EXIST_FOLLOW"); }

        Follow follow = new Follow(following, follower);
        followRepository.save(follow);
    }

    // Follow 관계 delete (following: 로그인한 유저 / follower: Path Variable로 입력받은 유저)
    public void deleteFollow(Long userId, String userNickname) {
        User following = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        User follower = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        Follow follow = followRepository.findByFollowingAndFollower(following, follower)
                // TODO : 커스텀 예외로 변경
                .orElseThrow(() -> new IllegalStateException("NOT_FOUND_FOLLOW"));
        followRepository.delete(follow);
    }
}
