package com.okbo_projects.domain.board.controller;


import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.request.CreateBoardRequest;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;










































































    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<Page<BoardReadAllPageResponse>> getBoardAllPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(boardService.getBoardAllPage(page, size));
    }

    // 게시글 구단별 전체 조회
    @GetMapping("/teams/{teamName}")
    public ResponseEntity<Page<BaordReadTeamPageResponse>> getBoardTeamAllPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String teamName
    ) {
        return ResponseEntity.ok(boardService.getBoardTeamAllPage(page, size, teamName));
    }

    // 팔로워 게시글 조회
    @GetMapping("/followers")
    public ResponseEntity<Page<BoardReadFollowPageResponse>> getBoardFollowAllPage(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(boardService.getBoardFollowAllPage(page, size, sessionUser.getUserId()));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long boardId) {
        boardService.deleteBoard(sessionUser.getUserId(), boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}