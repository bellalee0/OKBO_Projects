package com.okbo_projects.domain.comment.repository;

import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_COMMENT;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBoardId(Long boardId, Pageable pageable);

    default Comment findCommentById(Long commentId) {
        return findById(commentId).orElseThrow(() -> new CustomException(NOT_FOUND_COMMENT));
    }
}