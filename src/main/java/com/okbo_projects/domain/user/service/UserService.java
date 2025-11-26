package com.okbo_projects.domain.user.service;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.common.utils.PasswordEncoder;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.domain.follow.repository.FollowRepository;
import com.okbo_projects.domain.user.model.dto.UserDto;
import com.okbo_projects.domain.user.model.request.*;
import com.okbo_projects.domain.user.model.response.UserCreateResponse;
import com.okbo_projects.domain.user.model.response.UserGetMyProfileResponse;
import com.okbo_projects.domain.user.model.response.UserGetOtherProfileResponse;
import com.okbo_projects.domain.user.model.response.UserNicknameUpdateResponse;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.okbo_projects.common.exception.ErrorMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 회원가입
    public UserCreateResponse create(UserCreateRequest request) {

        if (userRepository.existsUserByNickname(request.getNickname())) {
            throw new CustomException(CONFLICT_USED_NICKNAME);
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new CustomException(CONFLICT_USED_EMAIL);
        }

        String encodingPassword = passwordEncoder.encode(request.getPassword());
        Team team = Team.valueOf(request.getFavoriteTeam());

        User user = new User(
                request.getNickname(),
                request.getEmail(),
                encodingPassword,
                team
        );

        userRepository.save(user);
        return UserCreateResponse.from(UserDto.from(user));
    }

    // 로그인
    public SessionUser login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());

        if (!user.isActivated()) {
            throw new CustomException(NOT_FOUND_USER);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNAUTHORIZED_WRONG_PASSWORD);
        }

        return SessionUser.from(UserDto.from(user));
    }

    // 내 프로필 조회
    @Transactional(readOnly = true)
    public UserGetMyProfileResponse getMyProfile(SessionUser sessionUser) {
        User user = userRepository.findUserById(sessionUser.getUserId());

        return UserGetMyProfileResponse.from(UserDto.from(user));
    }

    // 다른 유저 프로필 조회
    @Transactional(readOnly = true)
    public UserGetOtherProfileResponse getOtherProfile(String userNickname) {
        User user = userRepository.findUserByNickname(userNickname);

        if (!user.isActivated()) {
            throw new CustomException(NOT_FOUND_USER);
        }

        return UserGetOtherProfileResponse.from(UserDto.from(user));
    }

    // 유저 닉네임 변경(중복 불가)
    public UserNicknameUpdateResponse updateNickname(UserNicknameUpdateRequest request, SessionUser sessionUser) {

        if (userRepository.existsUserByNickname(request.getNickname())) {
            throw new CustomException(CONFLICT_USED_NICKNAME);
        }

        User user = userRepository.findUserById(sessionUser.getUserId());
        user.updateNickname(request.getNickname());
        userRepository.save(user);

        return UserNicknameUpdateResponse.from(UserDto.from(user));
    }

    // 유저 비밀번호 변경(현재 비밀번호 검증 및 새 비밀번호는 현재 비밀번호와 일치 불가)
    public void updatePassword(UserPasswordUpdateRequest request, SessionUser sessionUser) {
        User user = userRepository.findUserById(sessionUser.getUserId());

        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new CustomException(UNAUTHORIZED_WRONG_PASSWORD);
        }

        if (currentPassword.equals(newPassword)) {
            throw new CustomException(BAD_REQUEST_PASSWORD_SAME_AS_CURRENT);
        }

        String encodingPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodingPassword);
    }

    // 유저 삭제(회원 탈퇴, 팔로워&팔로잉 목록 삭제)
    public void delete(UserDeleteRequest request, SessionUser sessionUser) {
        User user = userRepository.findUserById(sessionUser.getUserId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNAUTHORIZED_WRONG_PASSWORD);
        }

        followRepository.deleteAllByFromUser(user);
        followRepository.deleteAllByToUser(user);

        user.deactivate();
        userRepository.save(user);
    }
}
