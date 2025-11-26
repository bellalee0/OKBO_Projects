package com.okbo_projects.domain.comment.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import com.okbo_projects.domain.comment.model.response.CommentUpdateResponse;
import com.okbo_projects.domain.comment.repository.CommentRepository;
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
