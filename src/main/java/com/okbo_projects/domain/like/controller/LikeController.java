package com.okbo_projects.domain.like.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.like.model.response.BoardLikesCountResponse;
import com.okbo_projects.domain.like.model.response.CommentLikesCountResponse;
import com.okbo_projects.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 게시글 좋아요 추가
    @PostMapping("/boards/{boardId}")
    public ResponseEntity<Void> addBoardLike(@PathVariable Long boardId,
                                             @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        likeService.addBoardLike(boardId, sessionUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Void> deleteBoardLike(@PathVariable Long boardId,
                                                @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        likeService.deleteBoardLike(boardId, sessionUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 게시글 좋아요 개수
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardLikesCountResponse> countBoardLikes(@PathVariable Long boardId) {
        BoardLikesCountResponse response = likeService.countBoardLikes(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 좋아요 추가
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<Void> addCommentLike(@PathVariable Long commentId,
                                               @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        likeService.addCommentLike(commentId, sessionUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentId,
                                                  @SessionAttribute(name = "loginUser") SessionUser sessionUser) {
        likeService.deleteCommentLike(commentId, sessionUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 좋아요 개수
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentLikesCountResponse> countCommentLikes(@PathVariable Long commentId) {
        CommentLikesCountResponse response = likeService.countCommentLikes(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}