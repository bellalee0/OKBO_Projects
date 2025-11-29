package com.okbo_projects.domain.like.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 해당 게시글에 좋아요 했는지 체크
    boolean existsByBoardAndUser(Board board, User user);

    // 게시글 좋아요 삭제
    void deleteByBoardAndUser(Board board, User user);

    // 해당 댓글에 좋아요 했는지 체크
    boolean existsByCommentAndUser(Comment comment, User user);

    // 댓글 좋아요 삭제
    void deleteByCommentAndUser(Comment comment, User user);

    // 게시글 삭제 시 좋아요 삭제
    void deleteByBoard(Board board);

    // 댓글 삭제 시 좋아요 삭제
    void deleteByComment(Comment comment);
}