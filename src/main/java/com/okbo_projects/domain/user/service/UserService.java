package com.okbo_projects.domain.user.service;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.common.utils.PasswordEncoder;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.domain.user.model.request.LoginRequest;
import com.okbo_projects.domain.user.model.request.UserCreateRequest;
import com.okbo_projects.domain.user.model.response.UserCreateResponse;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateResponse create(UserCreateRequest request) {
        // 닉네임 중복 검증
        if (userRepository.existsUserByNickname((request.getNickname()))) {
            // 닉네임 중복 예외 처리 추후 수정 예정
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }
        // 이메일 중복 검증
        if (userRepository.existsUserByEmail(request.getEmail())) {
            // 이메일 중복 예외 처리 추후 수정 예정
            throw new RuntimeException("해당 이메일로 가입한 계정이 존재합니다.");
        }
        // 비밀번호 암호화
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

    public SessionUser login(LoginRequest request) {
        // 예외 처리 추후 수정 예정
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일이 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 예외 처리 추후 수정 예정
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return new SessionUser(user.getId(), user.getEmail());
    }
}
