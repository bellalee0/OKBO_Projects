package com.okbo_projects.domain.like.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.like.model.response.BoardLikesCountResponse;
import com.okbo_projects.domain.like.model.response.CommentLikesCountResponse;
import com.okbo_projects.domain.like.repository.LikeRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시글 좋아요 추가
    public void addBoardLike(Long boardId, SessionUser sessionUser) {

        Board board = boardRepository.findBoardById(boardId);
        User user = userRepository.findUserById(sessionUser.getUserId());

        boolean checkLikeExistence = likeRepository.existsByBoardAndUser(board, user);
        // 예외 처리 추후 수정 예정
        // 한 게시글에 좋아요 여러번 하려는 경우
        if (checkLikeExistence) {
            throw new RuntimeException("이미 좋아요한 게시글입니다.");
        }

        Like like = new Like(
                user,
                board
        );

        likeRepository.save(like);
    }

    // 게시글 좋아요 취소
    public void deleteBoardLike(Long boardId, SessionUser sessionUser) {
        Board board = boardRepository.findBoardById(boardId);
        User user = userRepository.findUserById(sessionUser.getUserId());

        boolean checkLikeExistence = likeRepository.existsByBoardAndUser(board, user);
        // 예외 처리 추후 수정 예정
        // 좋아요 안했는데 취소하는 경우
        if (!checkLikeExistence) {
            throw new RuntimeException("좋아요를 누르지 않은 게시글입니다.");
        }

        likeRepository.deleteByBoardAndUser(board, user);
    }

    // 게시글 별 좋아요 개수
    public BoardLikesCountResponse countBoardLikes(Long boardId) {
        Board board = boardRepository.findBoardById(boardId);
        Long count = likeRepository.countByBoard(board);

        return new BoardLikesCountResponse(count);
    }

    // 댓글 좋아요 추가
    public void addCommentLike(Long commentId, SessionUser sessionUser) {

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = userRepository.findUserById(sessionUser.getUserId());

        boolean checkLikeExistence = likeRepository.existsByCommentAndUser(comment, user);
        // 예외 처리 추후 수정 예정
        // 한 게시글에 좋아요 여러번 하려는 경우
        if (checkLikeExistence) {
            throw new RuntimeException("이미 좋아요한 댓글입니다.");
        }

        Like like = new Like(
                user,
                comment
        );

        likeRepository.save(like);
    }

    // 댓글 좋아요 삭제
    public void deleteCommentLike(Long commentId, SessionUser sessionUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = userRepository.findUserById(sessionUser.getUserId());

        boolean checkLikeExistence = likeRepository.existsByCommentAndUser(comment, user);
        // 예외 처리 추후 수정 예정
        // 좋아요 안했는데 취소하는 경우
        if (!checkLikeExistence) {
            throw new RuntimeException("좋아요를 누르지 않은 댓글입니다.");
        }

        likeRepository.deleteByCommentAndUser(comment, user);
    }

    public CommentLikesCountResponse countCommentLikes(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Long count = likeRepository.countByComment(comment);

        return new CommentLikesCountResponse(count);
    }
}