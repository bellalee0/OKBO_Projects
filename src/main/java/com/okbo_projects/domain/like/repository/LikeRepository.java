package com.okbo_projects.domain.like.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByBoardAndUser(Board board, User user);

    boolean existsByBoardAndUser(Board board, User user);

    Long countByBoard(Board board);
}