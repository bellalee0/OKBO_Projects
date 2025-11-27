package com.okbo_projects.domain.board.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_BOARD;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByWriter(User user, Pageable pageable);

    Page<Board> findByTeam(Team team, Pageable pageable);

    @Query("""
            SELECT board
              FROM Board board
             WHERE board.writer.id IN (
                SELECT follow.toUser.id
                  FROM Follow follow
                 WHERE follow.fromUser.id = :userId)
            """)
    Page<Board> findByFollowerBoard(@Param("userId") Long userId, Pageable pageable);

    // 전체 게시물 검색
    @Query("""
            SELECT board
              FROM Board board
             WHERE (:title IS NULL OR :title = '' OR board.title LIKE %:title%)
               AND (:writer IS NULL OR :writer = '' OR board.writer.nickname LIKE CONCAT('%', :writer, '%'))
               AND (:startDate IS NULL OR board.createdAt >= :startDate)
               AND (:endDate IS NULL OR board.createdAt <= :endDate)
            """)
    Page<Board> searchAllBoards(
            @Param("title") String title,
            @Param("writer") String writer,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // 팀 별 게시물 검색
    @Query("""
            SELECT board
              FROM Board board
             WHERE board.team = :team
               AND (:title IS NULL OR board.title LIKE %:title%)
               AND (:writer IS NULL OR board.writer.nickname LIKE %:writer%)
               AND (:startDate IS NULL OR board.createdAt >= :startDate)
               AND (:endDate IS NULL OR board.createdAt <= :endDate)
            """)
    Page<Board> searchTeamBoards(
            @Param("team") Team team,
            @Param("title") String title,
            @Param("writer") String writer,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // 내 게시물 검색
    @Query("""
            SELECT board
              FROM Board board
             WHERE board.writer.id = :userId
               AND (:title IS NULL OR board.title LIKE %:title%)
               AND (:startDate IS NULL OR board.createdAt >= :startDate)
               AND (:endDate IS NULL OR board.createdAt <= :endDate)
            """)
    Page<Board> searchMyBoards(
            @Param("userId") Long userId,
            @Param("title") String title,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // 팔로우 게시물 검색
    @Query("""
            SELECT board
              FROM Board board
             WHERE board.writer.id IN (
                SELECT follow.toUser.id
                  FROM Follow follow
                 WHERE follow.fromUser.id = :userId)
               AND (:title IS NULL OR :title = '' OR board.title LIKE %:title%)
               AND (:writer IS NULL OR :writer = '' OR board.writer.nickname LIKE CONCAT('%', :writer, '%'))
               AND (:startDate IS NULL OR board.createdAt >= :startDate)
               AND (:endDate IS NULL OR board.createdAt <= :endDate)
            """)
    Page<Board> searchByFollowerBoard(
            @Param("userId") Long userId,
            @Param("title") String title,
            @Param("writer") String writer,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    default Board findBoardById(Long boardId) {
        return findById(boardId).orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));
    }
}