package com.okbo_projects.domain.board.controller;

import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.request.BoardCreateRequest;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        BoardDetailedInquiryResponse result = boardService.detailedInquiryBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //내가 작성한 게시글 목록 조회
    @GetMapping("/myBoard")
    public ResponseEntity<Page<BoardGetMyArticlesResponse>> viewListOfMyArticlesWritten(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<BoardGetMyArticlesResponse> result = boardService.viewListOfMyArticlesWritten(sessionUser, title, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<Page<BoardGetAllPageResponse>> getBoardAllPage(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardGetAllPageResponse> result = boardService.getBoardAllPage(title, writer, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 구단별 전체 조회
    @GetMapping("/teams/{teamName}")
    public ResponseEntity<Page<BoardGetTeamPageResponse>> getBoardTeamAllPage(
            @PathVariable String teamName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable

    ) {
        Page<BoardGetTeamPageResponse> result = boardService.getBoardTeamAllPage(teamName, title, writer, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 팔로워 게시글 조회
    @GetMapping("/followings")
    public ResponseEntity<Page<BoardGetFollowPageResponse>> getBoardFollowAllPage(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardGetFollowPageResponse> result = boardService.getBoardFollowAllPage(sessionUser, pageable);
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