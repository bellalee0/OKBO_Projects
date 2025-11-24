package com.okbo_projects.domain.board.controller;


import com.okbo_projects.common.entity.User;
import com.okbo_projects.domain.board.model.request.CreateBoardRequest;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import com.okbo_projects.domain.board.model.response.CreateBoardResponse;
import com.okbo_projects.domain.board.model.response.DetailedInquiryBoardResponse;
import com.okbo_projects.domain.board.model.response.UpdateBoardResponse;
import com.okbo_projects.domain.board.model.response.ViewListOfMyArticlesWrittenResponse;
import com.okbo_projects.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //게시글 생성
    @PostMapping("/create")
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestBody CreateBoardRequest request, Principal principal){

        return ResponseEntity.ok(boardService.createBoard(1L, request));

    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<UpdateBoardResponse> updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequest request){
        return ResponseEntity.ok(boardService.updateBoard(id, request));
    }
    //게시글 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<DetailedInquiryBoardResponse> detailedInquiryBoard(@PathVariable Long id){
        return ResponseEntity.ok(DetailedInquiryBoardResponse.from(boardService.detailedInquiryBoard(id)));

    }
    //내가 작성한 게시글 목록 조회
    @GetMapping("/myboard")
    public ResponseEntity<List<ViewListOfMyArticlesWrittenResponse>> viewListOfMyArticlesWritten(Principal principal){
        return ResponseEntity.ok(boardService.viewListOfMyArticlesWritten(1L));
    }


}
