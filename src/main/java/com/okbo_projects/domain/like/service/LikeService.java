package com.okbo_projects.domain.like.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
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

import static com.okbo_projects.common.exception.ErrorMessage.*;

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
        if (checkLikeExistence) {
            throw new CustomException(CONFLICT_ALREADY_BOARD_LIKE);
        }

        board.addLikes();

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
        if (!checkLikeExistence) {
            throw new CustomException(BAD_REQUEST_NOT_LIKE_UNLIKE_ON_BOARD);
        }

        board.minusLikes();

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
        if (checkLikeExistence) {
            throw new CustomException(CONFLICT_ALREADY_COMMENT_LIKE);
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
        if (!checkLikeExistence) {
            throw new CustomException(BAD_REQUEST_NOT_LIKE_UNLIKE_ON_COMMENT);
        }

        likeRepository.deleteByCommentAndUser(comment, user);
    }

    // 댓글 별 좋아요 개수
    public CommentLikesCountResponse countCommentLikes(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Long count = likeRepository.countByComment(comment);

        return new CommentLikesCountResponse(count);
    }
}