package com.okbo_projects.domain.user.repository;

import com.okbo_projects.common.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(@Email(message = "올바른 이메일 형식이 아닙니다.") String email);

    Optional<User> findByEmail(String email);

}
