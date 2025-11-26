package com.okbo_projects.domain.comment.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.model.dto.CommentDto;
import com.okbo_projects.domain.comment.model.request.CommentCreateRequest;
import com.okbo_projects.domain.comment.model.response.CommentCreateResponse;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.okbo_projects.common.exception.ErrorMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentCreateResponse createComment(Long boardId, SessionUser sessionUser, CommentCreateRequest request) {
        Board board = findByBoardId(boardId);
        User user = findByUserId(sessionUser.getUserId());
        Comment comment = new Comment(
                request.getComments(),
                user,
                board
        );
        commentRepository.save(comment);
        CommentDto commentDto = CommentDto.from(comment);
        return CommentCreateResponse.from(commentDto);
    }








































    // 회원 확인
    private User findByUserId(Long userId) {
        return userRepository.findUserById(userId);
    }

    // 게시물 확인
    private Board findByBoardId(Long boardId) {
        return boardRepository.findBoardById(boardId);
    }

    // 작성자 일치 확인
    private void matchedWriter(Long userId, Long boardUserId) {
        if(!userId.equals(boardUserId)) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
        }
    }
}
