package com.okbo_projects.domain.board.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByWriter(User user);
}
