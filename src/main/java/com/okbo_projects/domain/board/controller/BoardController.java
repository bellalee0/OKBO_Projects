package com.okbo_projects.domain.board.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.request.BoardCreateRequest;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 생성
    @PostMapping
    public ResponseEntity<BoardCreateResponse> createBoard(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody BoardCreateRequest request
    ){
        BoardCreateResponse result = boardService.createBoard(sessionUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardUpdateResponse> updateBoard(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long boardId,
            @Valid @RequestBody BoardUpdateRequest request
    ){
        BoardUpdateResponse result = boardService.updateBoard(sessionUser, boardId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //게시글 상세조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDetailedInquiryResponse> detailedInquiryBoard(
            @PathVariable Long boardId
    ){
        BoardDetailedInquiryResponse result = BoardDetailedInquiryResponse.from(boardService.detailedInquiryBoard(boardId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //내가 작성한 게시글 목록 조회
    @GetMapping("/myBoard")
    public ResponseEntity<Page<BoardGetMyArticlesResponse>> viewListOfMyArticlesWritten(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<BoardGetMyArticlesResponse> result = boardService.viewListOfMyArticlesWritten(sessionUser,page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<Page<BoardGetAllPageResponse>> getBoardAllPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BoardGetAllPageResponse> result = boardService.getBoardAllPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 구단별 전체 조회
    @GetMapping("/teams/{teamName}")
    public ResponseEntity<Page<BoardGetTeamPageResponse>> getBoardTeamAllPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String teamName
    ) {
        Page<BoardGetTeamPageResponse> result = boardService.getBoardTeamAllPage(page, size, teamName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 팔로워 게시글 조회
    @GetMapping("/followings")
    public ResponseEntity<Page<BoardGetFollowPageResponse>> getBoardFollowAllPage(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BoardGetFollowPageResponse> result = boardService.getBoardFollowAllPage(page, size, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long boardId
    ) {
        boardService.deleteBoard(sessionUser, boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}