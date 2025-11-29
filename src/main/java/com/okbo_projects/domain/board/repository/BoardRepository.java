package com.okbo_projects.domain.board.repository;

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_BOARD;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.Team;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Override
    @Query("""
        SELECT board
        FROM Board board
        WHERE board.isDeleted = false
        """)
    Page<Board> findAll(Pageable pageable);

    @Override
    @Query("""
        SELECT board
        FROM Board board
        WHERE board.id = :id AND board.isDeleted = false
        """)
    Optional<Board> findById(@Param("id") Long id);

    @Query("""
        SELECT board
        FROM Board board
        WHERE board.writer = :user AND board.isDeleted = false
        """)
    Page<Board> findByWriter(@Param("user") User user, Pageable pageable);

    @Query("""
        SELECT board
        FROM Board board
        WHERE board.team = :team AND board.isDeleted = false
        """)
    Page<Board> findByTeam(@Param("team") Team team, Pageable pageable);

    @Query("""
        SELECT board
          FROM Board board
         WHERE board.writer.id IN (
            SELECT follow.toUser.id
              FROM Follow follow
             WHERE follow.fromUser.id = :userId)
            AND board.isDeleted = false
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
           AND board.isDeleted = false
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
           AND board.isDeleted = false
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
           AND board.isDeleted = false
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
           AND board.isDeleted = false
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