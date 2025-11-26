package com.okbo_projects.domain.follow.service;

import com.okbo_projects.common.entity.Follow;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.follow.model.dto.FollowDto;
import com.okbo_projects.domain.follow.model.response.*;
import com.okbo_projects.domain.follow.repository.FollowRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.okbo_projects.common.exception.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // Follow 관계 create (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    public void createFollow(SessionUser sessionUser, String userNickname) {
        User fromUser = userRepository.findUserById(sessionUser.getUserId());
        User toUser = userRepository.findUserByNickname(userNickname);

        if (fromUser.equals(toUser)) { throw new CustomException(BAD_REQUEST_NOT_ALLOWED_SELF_FOLLOW); }
        boolean checkFollowExistence = followRepository.existsByFromUserAndToUser(fromUser, toUser);
        if (checkFollowExistence) { throw new CustomException(CONFLICT_ALREADY_FOLLOWING); }
        if (!toUser.isActivated()) { throw new CustomException(NOT_FOUND_USER); }

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);
    }

    // Follow 관계 delete (fromUser: 로그인한 유저 / toUser: Path Variable로 입력받은 유저)
    public void deleteFollow(SessionUser sessionUser, String userNickname) {
        User fromUser = userRepository.findUserById(sessionUser.getUserId());
        User toUser = userRepository.findUserByNickname(userNickname);

        Follow follow = followRepository.findFollowByFromUserAndToUser(fromUser, toUser);

        followRepository.delete(follow);
    }

    // Following, Follower 수 count
    public FollowCountResponse countFollow(SessionUser sessionUser, String userNickname) {
        User user;
        if (userNickname == null) {
            user = userRepository.findUserById(sessionUser.getUserId());
        } else {
            user = userRepository.findUserByNickname(userNickname);
        }

        Long following = followRepository.countByFromUser(user);
        Long follower = followRepository.countByToUser(user);
        return FollowCountResponse.from(following, follower);
    }

    // Following 유저 리스트 조회 (생성일 기준 내림차순 정렬)
    public Page<FollowGetFollowingListResponse> getFollowingList(SessionUser sessionUser, int page, int size, String userNickname) {
        User user;
        if (userNickname == null) {
            user = userRepository.findUserById(sessionUser.getUserId());
        } else {
            user = userRepository.findUserByNickname(userNickname);
        }

        boolean existsFollowingList = followRepository.existsByFromUser(user);
        if (!existsFollowingList) {
            throw new CustomException(NOT_FOUND_FOLLOWING);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return followRepository.findByFromUser(user, pageable)
                .map(follow -> FollowGetFollowingListResponse.from(FollowDto.from(follow)));
    }

    // Follower 유저 리스트 조회 (생성일 기준 내림차순 정렬)
    public Page<FollowGetFollowerListResponse> getFollowerList(SessionUser sessionUser, int page, int size, String userNickname) {
        User user;
        if (userNickname == null) {
            user = userRepository.findUserById(sessionUser.getUserId());
        } else {
            user = userRepository.findUserByNickname(userNickname);
        }

        boolean existsFollowerList = followRepository.existsByToUser(user);
        if (!existsFollowerList) {
            throw new CustomException(NOT_FOUND_FOLLOWER);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return followRepository.findByToUser(user, pageable)
                .map(follow -> FollowGetFollowerListResponse.from(FollowDto.from(follow)));
    }
}
