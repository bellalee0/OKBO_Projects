package com.okbo_projects.domain.user.service;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.common.utils.PasswordEncoder;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.domain.user.model.request.LoginRequest;
import com.okbo_projects.domain.user.model.request.UserCreateRequest;
import com.okbo_projects.domain.user.model.request.UserNicknameUpdateRequest;
import com.okbo_projects.domain.user.model.request.UserPasswordUpdateRequest;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        return new UserCreateResponse(user);
    }

    // 로그인
    public SessionUser login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNAUTHORIZED_WRONG_PASSWORD);
        }

        return new SessionUser(user.getId(), user.getEmail(), user.getNickname());
    }

    // 내 프로필 조회
    @Transactional(readOnly = true)
    public UserGetMyProfileResponse getMyProfile(SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getUserId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        return new UserGetMyProfileResponse(user);
    }

    // 다른 유저 프로필 조회
    @Transactional(readOnly = true)
    public UserGetOtherProfileResponse getOtherProfile(String userNickname) {
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        return new UserGetOtherProfileResponse(user);
    }

    // 유저 닉네임 변경
    public UserNicknameUpdateResponse updateNickname(UserNicknameUpdateRequest request, SessionUser sessionUser) {

        if (userRepository.existsUserByNickname(request.getNickname())) {
            throw new CustomException(CONFLICT_USED_NICKNAME);
        }

        User user = userRepository.findUserById(sessionUser.getUserId());
        user.updateNickname(request.getNickname());
        userRepository.save(user);

        return new UserNicknameUpdateResponse(user);
    }

    // 유저 비밀번호 변경
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
}
