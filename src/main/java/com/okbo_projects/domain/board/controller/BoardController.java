package com.okbo_projects.domain.board.controller;

import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.domain.board.model.request.BoardCreateRequest;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardCreateResponse result = boardService.createBoard(loginUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardUpdateResponse> updateBoard(
            @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @PathVariable long boardId,
            @Valid @RequestBody BoardUpdateRequest request
    ) {
        BoardUpdateResponse result = boardService.updateBoard(loginUser, boardId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //게시글 상세조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDetailedInquiryResponse> detailedInquiryBoard(
            @PathVariable long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        BoardDetailedInquiryResponse result = boardService.detailedInquiryBoard(boardId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //내가 작성한 게시글 목록 조회
    @GetMapping("/my-board")
    public ResponseEntity<Page<BoardGetMyArticlesResponse>> viewListOfMyArticlesWritten(
            @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardGetMyArticlesResponse> result = boardService.viewListOfMyArticlesWritten(loginUser, title, startDate, endDate, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<Page<BoardGetAllPageResponse>> getBoardAllPage(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
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
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardGetTeamPageResponse> result = boardService.getBoardTeamAllPage(teamName, title, writer, startDate, endDate, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 팔로워 게시글 조회
    @GetMapping("/followings")
    public ResponseEntity<Page<BoardGetFollowPageResponse>> getBoardFollowAllPage(
            @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardGetFollowPageResponse> result = boardService.getBoardFollowAllPage(loginUser, title, writer, startDate, endDate, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @RequestAttribute(name = "loginUser") LoginUser loginUser,
            @PathVariable long boardId
    ) {
        boardService.deleteBoard(loginUser, boardId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}