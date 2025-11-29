package com.okbo_projects.domain.like.service;

import static com.okbo_projects.common.exception.ErrorMessage.BAD_REQUEST_NOT_LIKE_UNLIKE_ON_BOARD;
import static com.okbo_projects.common.exception.ErrorMessage.BAD_REQUEST_NOT_LIKE_UNLIKE_ON_COMMENT;
import static com.okbo_projects.common.exception.ErrorMessage.CONFLICT_ALREADY_BOARD_LIKE;
import static com.okbo_projects.common.exception.ErrorMessage.CONFLICT_ALREADY_COMMENT_LIKE;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.repository.CommentRepository;
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
    public void createBoardLike(long boardId, LoginUser loginUser) {

        Board board = boardRepository.findBoardById(boardId);
        User user = userRepository.findUserById(loginUser.getUserId());

        if (likeRepository.existsByBoardAndUser(board, user)) {
            throw new CustomException(CONFLICT_ALREADY_BOARD_LIKE);
        }

        Like like = new Like(user, board);
        board.addLikes();

        likeRepository.save(like);
    }

    // 게시글 좋아요 취소
    public void deleteBoardLike(long boardId, LoginUser loginUser) {

        Board board = boardRepository.findBoardById(boardId);
        User user = userRepository.findUserById(loginUser.getUserId());

        if (!likeRepository.existsByBoardAndUser(board, user)) {
            throw new CustomException(BAD_REQUEST_NOT_LIKE_UNLIKE_ON_BOARD);
        }

        likeRepository.deleteByBoardAndUser(board, user);
        board.minusLikes();
    }

    // 댓글 좋아요 추가
    public void createCommentLike(long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findCommentById(commentId);
        User user = userRepository.findUserById(loginUser.getUserId());

        if (likeRepository.existsByCommentAndUser(comment, user)) {
            throw new CustomException(CONFLICT_ALREADY_COMMENT_LIKE);
        }

        Like like = new Like(user, comment);

        likeRepository.save(like);
        comment.addLikes();
    }

    // 댓글 좋아요 삭제
    public void deleteCommentLike(long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findCommentById(commentId);
        User user = userRepository.findUserById(loginUser.getUserId());

        if (!likeRepository.existsByCommentAndUser(comment, user)) {
            throw new CustomException(BAD_REQUEST_NOT_LIKE_UNLIKE_ON_COMMENT);
        }

        likeRepository.deleteByCommentAndUser(comment, user);
        comment.minusLikes();
    }
}