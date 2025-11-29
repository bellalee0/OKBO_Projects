package com.okbo_projects.domain.comment.controller;

import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.comment.model.request.CommentCreateRequest;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import com.okbo_projects.domain.comment.model.response.CommentCreateResponse;
import com.okbo_projects.domain.comment.model.response.CommentGetAllResponse;
import com.okbo_projects.domain.comment.model.response.CommentUpdateResponse;
import com.okbo_projects.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/boards/{boardId}")
    public ResponseEntity<CommentCreateResponse> createComment(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @Valid @RequestBody CommentCreateRequest request,
        @PathVariable long boardId
    ) {
        CommentCreateResponse result = commentService.createComment(boardId, loginUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 댓글 전체 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<Page<CommentGetAllResponse>> getAllComment(
        @PathVariable long boardId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<CommentGetAllResponse> result = commentService.getAllComment(boardId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @PathVariable long commentId,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentUpdateResponse result = commentService.updateComment(commentId, loginUser, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @RequestAttribute(name = "loginUser") LoginUser loginUser,
        @PathVariable long commentId
    ) {
        commentService.deleteComment(commentId, loginUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}