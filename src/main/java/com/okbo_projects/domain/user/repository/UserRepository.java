package com.okbo_projects.domain.user.repository;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임 존재 여부 확인
    boolean existsUserByNickname(String nickname);

    // 이메일 존재 여부 확인
    boolean existsUserByEmail(String email);

    // 이메일로 유저 조회
    Optional<User> findByEmail(String email);

    // 닉네임으로 유저 조회
    Optional<User> findByNickname(String nickname);

    // id로 유저 조회
    default User findUserById(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    // 닉네임으로 유저 조회
    default User findUserByNickname(String nickname) {
        return findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    // 이메일로 유저 조회
    default User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}
