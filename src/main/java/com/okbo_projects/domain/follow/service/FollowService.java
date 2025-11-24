package com.okbo_projects.domain.follow.service;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
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
                // TODO : 커스텀 예외로 변경
                .orElseThrow(() -> new IllegalStateException("NOT_FOUND_USER"));
        User follower = userRepository.findByNickname(userNickname)
                // TODO : 커스텀 예외로 변경
                .orElseThrow(() -> new IllegalStateException("NOT_FOUND_USER"));
        // TODO : following, follower가 같을 때, 이미 형성된 관계일 때 예외 처리
        Follow follow = new Follow(following, follower);
        followRepository.save(follow);
    }
}
