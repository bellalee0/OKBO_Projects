package com.okbo_projects.domain.board.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByWriter(User user);


    Page<Board> findByTeam(Team team, Pageable pageable);

//    @Query("""
//        SELECT (
//            board.id,
//            board.title,
//            board.team,
//            board.writer
//        )
//        FROM Board board
//        WHERE board.writer IN (
//                SELECT follow.toUser.id
//                  FROM Follow follow
//                 WHERE follow.fromUser.id = :userId
//                )
//        """)
//    Page<Board> findByFollowerBoard(@Param("userId")Long userId, Pageable pageable);
}
