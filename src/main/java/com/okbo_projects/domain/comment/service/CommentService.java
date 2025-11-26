package com.okbo_projects.domain.comment.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import com.okbo_projects.domain.comment.model.response.CommentUpdateResponse;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.okbo_projects.common.exception.ErrorMessage.FORBIDDEN_ONLY_WRITER;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //댓글 수정
    public CommentUpdateResponse updateComment(SessionUser sessionUser, Long commentId, CommentUpdateRequest request) {
        Comment comment = findByCommentId(commentId);
        Long userId = sessionUser.getUserId();
        if (!comment.getWriter().getId().equals(userId)) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
        }
        comment.update(request);
        commentRepository.save(comment);
        return CommentUpdateResponse.from(comment.toDto());
    }

    //댓글 삭제
    public void deleteComment(SessionUser sessionUser, Long commentId) {
        Comment comment = findByCommentId(commentId);
        Long userId = sessionUser.getUserId();
        if (!comment.getWriter().getId().equals(userId)) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
            }
        commentRepository.delete(comment);
    }

    private Comment findByCommentId(Long commentId) {
        return commentRepository.findCommentById(commentId);
    }

//    private void matchedCommentWriter(Long userId, Long boardUserId) {
//        if(!userId.equals(commentUserId)) {
//            throw new CustomException(FORBIDDEN_ONLY_WRITER);
//        }
//    }


}


