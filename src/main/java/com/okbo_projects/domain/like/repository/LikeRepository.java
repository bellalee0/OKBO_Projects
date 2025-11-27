package com.okbo_projects.domain.like.repository;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 게시글 좋아요 삭제
    void deleteByBoardAndUser(Board board, User user);

    // 댓글 좋아요 삭제
    void deleteByCommentAndUser(Comment comment, User user);

    // 해당 게시글에 좋아요했는지 체크
    boolean existsByBoardAndUser(Board board, User user);

    // 해당 댓글에 좋아요했는지 체크
    boolean existsByCommentAndUser(Comment comment, User user);

    // 게시글 별 좋아요 카운트
    Long countByBoard(Board board);

    // 댓글 별 좋아요 카운트
    Long countByComment(Comment comment);

    void deleteByBoard(Board board);
}