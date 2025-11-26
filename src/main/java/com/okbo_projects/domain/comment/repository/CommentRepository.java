package com.okbo_projects.domain.comment.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_BOARD;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentById(Long boardId) {
        return findById(boardId).orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));
    }

    Page<Comment> findByBoard_Id(Long boardId, Pageable pageable);

    Long countByBoard(Board board);

}


