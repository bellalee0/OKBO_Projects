package com.okbo_projects.domain.follow.service;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import com.okbo_projects.domain.follow.model.Response.FollowCountResponse;
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

    // Follow 관계 create (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    public void createFollow(Long userId, String userNickname) {
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        User toUser = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        // TODO : 커스텀 예외로 변경
        if (fromUser.equals(toUser)) { throw new IllegalStateException("SAME_USERS"); }
        boolean checkFollowExistence = followRepository.existsByFromUserAndToUser(fromUser, toUser);
        // TODO : 커스텀 예외로 변경
        if (checkFollowExistence) { throw new IllegalStateException("EXIST_FOLLOW"); }

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);
    }

    // Follow 관계 delete (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    public void deleteFollow(Long userId, String userNickname) {
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        User toUser = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
                // TODO : 커스텀 예외로 변경
                .orElseThrow(() -> new IllegalStateException("NOT_FOUND_FOLLOW"));
        followRepository.delete(follow);
    }

    // Following, Follower 수 count
    public FollowCountResponse countFollow(Long userId, String userNickname) {
        User user;
        if (userNickname == null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        } else {
            user = userRepository.findByNickname(userNickname)
                    .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        }

        long following = followRepository.countByFromUser(user);
        long follower = followRepository.countByToUser(user);
        return new FollowCountResponse(following, follower);
    }
}
