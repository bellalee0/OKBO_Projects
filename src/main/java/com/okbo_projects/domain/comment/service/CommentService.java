package com.okbo_projects.domain.comment.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.model.dto.CommentDto;
import com.okbo_projects.domain.comment.model.request.CommentCreateRequest;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import com.okbo_projects.domain.comment.model.response.CommentCreateResponse;
import com.okbo_projects.domain.comment.model.response.CommentGetAllResponse;
import com.okbo_projects.domain.comment.model.response.CommentUpdateResponse;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.like.repository.LikeRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final LikeRepository likeRepository;

    // 댓글 생성
    public CommentCreateResponse createComment(Long boardId, LoginUser loginUser, CommentCreateRequest request) {

        Board board = findByBoardId(boardId);

        User user = findByUserId(loginUser.getUserId());

        Comment comment = new Comment(
                request.getComments(),
                user,
                board
        );

        commentRepository.save(comment);

        board.addComments();

        CommentDto commentDto = CommentDto.from(comment);

        return CommentCreateResponse.from(commentDto);
    }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public Slice<CommentGetAllResponse> getAllComment(Long boardId, Pageable pageable) {

        Board board = findByBoardId(boardId);

        Page<Comment> commentPage = commentRepository.findByBoardId(board.getId(), pageable);

        return commentPage.map(i -> CommentGetAllResponse.from(CommentDto.from(i)));
    }

    //댓글 수정
    public CommentUpdateResponse updateComment(LoginUser loginUser, Long commentId, CommentUpdateRequest request) {

        Comment comment = findByCommentId(commentId);

        matchedWriter(loginUser.getUserId(), comment.getWriter().getId());

        comment.update(request);

        commentRepository.save(comment);

        return CommentUpdateResponse.from(CommentDto.from(comment));
    }

    //댓글 삭제
    public void deleteComment(LoginUser loginUser, Long commentId) {

        Comment comment = findByCommentId(commentId);

        matchedWriter(loginUser.getUserId(), comment.getWriter().getId());

        likeRepository.deleteByComment(comment);

        commentRepository.delete(comment);

        Board board = findByBoardId(comment.getBoard().getId());

        board.minusComments();
    }

    // 댓글 확인
    private Comment findByCommentId(Long commentId) {
        return commentRepository.findCommentById(commentId);
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
    private void matchedWriter(Long userId, Long CommentUserId) {
        if (!userId.equals(CommentUserId)) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
        }
    }
}