package com.okbo_projects.domain.comment.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.comment.model.request.CommentCreateRequest;
import com.okbo_projects.domain.comment.model.response.CommentCreateResponse;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import com.okbo_projects.domain.comment.model.response.CommentGetAllResponse;
import com.okbo_projects.domain.comment.model.response.CommentUpdateResponse;
import com.okbo_projects.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/boards/{boardId}")
    public ResponseEntity<CommentCreateResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CommentCreateRequest request,
            @PathVariable Long boardId
    ){
        CommentCreateResponse result = commentService.createComment(boardId, sessionUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 댓글 전체 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<Slice<CommentGetAllResponse>> getAllComment(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Slice<CommentGetAllResponse> result = commentService.getAllComment(boardId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ){
        CommentUpdateResponse result = commentService.updateComment(sessionUser,commentId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(sessionUser,commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
