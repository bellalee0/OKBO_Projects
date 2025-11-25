package com.okbo_projects.domain.user.repository;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(@Email(message = "올바른 이메일 형식이 아닙니다.") String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname (String nickname);

    default User findUserById(Long id){
        return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    default User findUserByEmail(String email){
        return findByEmail(email).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}
