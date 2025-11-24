package com.okbo_projects.domain.board.repository;

import com.okbo_projects.common.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

//
//
//
//
//
//    Page<Board> findByTeam(String teamName, Pageable pageable);
//
//
//    Page<Board> findBoardFollowAllPage(Pageable pageable);
}
