package com.okbo_projects.domain.board.controller;


import com.okbo_projects.domain.board.model.request.CreateBoardRequest;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

//    //게시글 생성
//    @PostMapping("/create")
//    public ResponseEntity<CreateBoardResponse> createBoard(@RequestBody CreateBoardRequest request, Principal principal){
//
//        return ResponseEntity.ok(boardService.createBoard(1L, request));
//
//    }
//
//    //게시글 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequest request){
//        return ResponseEntity.ok(boardService.updateBoard(id, request));
//    }
//    //게시글 상세조회
//    @GetMapping("/{id}")
//    public ResponseEntity<DetailedInquiryBoardResponse> detailedInquiryBoard(@PathVariable Long id){
//        return ResponseEntity.ok(DetailedInquiryBoardResponse.from(boardService.detailedInquiryBoard(id)));
//
//    }
//    //내가 작성한 게시글 목록 조회
//    @GetMapping("/myboard")
//    public ResponseEntity<List<ViewListOfMyArticlesWrittenResponse>> viewListOfMyArticlesWritten(Principal principal){
//        return ResponseEntity.ok(boardService.viewListOfMyArticlesWritten(1L));
//    }
//
//
//
//
//
//
//
//
//
//    /**
//     * 게시물 전체 조회
//     */
//    @GetMapping
//    public ResponseEntity<Page<BoardReadAllPageResponse>> getBoardAllPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
//        return ResponseEntity.ok(boardService.getBoardAllPage(pageable));
//    }
//
//
//    /**
//     * 게시글 구단별 조회
//     */
//    @GetMapping("/teams/{teamName}")
//    public ResponseEntity<Page<BaordReadTeamPageResponse>> getBoardTeamAllPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @PathVariable String teamName
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
//        return ResponseEntity.ok(boardService.getBoardTeamAllPage(teamName, pageable));
//    }
//
//    /**
//     * 게시글 팔로우 조회
//     */
//    @GetMapping("/following")
//    public ResponseEntity<Page<BoardReadFollowPageResponse>> getBoardFollowAllPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
//        return ResponseEntity.ok(boardService.getBoardFollowAllPage(pageable));
//    }
//
//    /**
//     * 게시글 삭제
//     */
//    @DeleteMapping("/{boardId}")
//    public ResponseEntity<Void> deleteBoard(
//            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
//            @PathVariable Long boardId) {
//
//        return ResponseEntity.ok(boardService.deleteBoard(sessionUser.getUserId(), boardId));
//
//    }
}