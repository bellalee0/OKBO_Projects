package com.okbo_projects.domain.board.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_BOARD;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByWriter(User user);

    Page<Board> findByTeam(Team team, Pageable pageable);

    @Query(
            value = """
        SELECT board
        FROM Board board
        WHERE board.writer.id IN (
            SELECT follow.toUser.id
            FROM Follow follow
            WHERE follow.fromUser.id = :userId
        )
        """,
            countQuery = """
        SELECT COUNT(board)
        FROM Board board
        WHERE board.writer.id IN (
            SELECT follow.toUser.id
            FROM Follow follow
            WHERE follow.fromUser.id = :userId
        )
        """
    )
    Page<Board> findByFollowerBoard(@Param("userId") Long userId, Pageable pageable);

    default Board findBoardById(Long boardId) {
        return findById(boardId).orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));
    }
}
