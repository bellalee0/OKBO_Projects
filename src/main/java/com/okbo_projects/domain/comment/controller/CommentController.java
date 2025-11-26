package com.okbo_projects.domain.comment.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.comment.model.request.CommentCreateRequest;
import com.okbo_projects.domain.comment.model.response.CommentCreateResponse;
import com.okbo_projects.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}
