package com.okbo_projects.domain.comment.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {



    default Comment findCommentById(Long commentId) {
        return findById(commentId).orElseThrow(() ->  new RuntimeException("존재하지 않는 댓글입니다."));
    }

    Page<Comment> findByBoard_Id(Long boardId, Pageable pageable);


    int countByBoard(Board board);
}


