package com.okbo_projects.domain.comment.repository;

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_COMMENT;

import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.exception.CustomException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        select comment
        from Comment comment
        where comment.board.id = :boardId and comment.isDeleted = false
        """)
    Page<Comment> findByBoardId(@Param("boardId") long boardId, Pageable pageable);

    List<Comment> findByBoardId(@Param("boardId") long boardId);

    default Comment findCommentById(long commentId) {
        return findById(commentId).orElseThrow(() -> new CustomException(NOT_FOUND_COMMENT));
    }
}