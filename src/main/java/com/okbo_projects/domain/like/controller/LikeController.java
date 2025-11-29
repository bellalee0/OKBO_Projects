package com.okbo_projects.domain.like.controller;

import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요 추가
    @PostMapping("/boards/{boardId}")
    public ResponseEntity<Void> createBoardLike(
        @PathVariable long boardId,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        likeService.createBoardLike(boardId, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Void> deleteBoardLike(
        @PathVariable long boardId,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        likeService.deleteBoardLike(boardId, loginUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 좋아요 추가
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<Void> createCommentLike(
        @PathVariable long commentId,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        likeService.createCommentLike(commentId, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentLike(
        @PathVariable long commentId,
        @RequestAttribute(name = "loginUser") LoginUser loginUser
    ) {
        likeService.deleteCommentLike(commentId, loginUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}