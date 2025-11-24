package com.okbo_projects.domain.board.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.domain.board.model.dto.BoardDto;
import com.okbo_projects.domain.board.model.request.CreateBoardRequest;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import com.okbo_projects.domain.board.model.response.CreateBoardResponse;
import com.okbo_projects.domain.board.model.response.UpdateBoardResponse;
import com.okbo_projects.domain.board.model.response.ViewListOfMyArticlesWrittenResponse;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 생성
    public CreateBoardResponse createBoard(SessionUser sessionUser,User writer, CreateBoardRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER));
        Board board = new Board(
                request.getTitle(),
                request.getContent(),
                writer
        );
        boardRepository.save(board);
        BoardDto dto = BoardDto.from(board);

        return CreateBoardResponse.from(dto);
    }

    //게시글 수정
    public UpdateBoardResponse updateBoard(SessionUser sessionUser,User writer, UpdateBoardRequest request) {
        Board board = boardRepository.findById(writer.getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD));

        if(!sessionUser.getId().equals(board.getWriter().getId())) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
        }
        board.update(request);
        boardRepository.save(board);
        BoardDto dto = BoardDto.from(board);
        return UpdateBoardResponse.from(dto);

    }
    //게시글 상세조회
    @Transactional(readOnly = true)
    public BoardDto detailedInquiryBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD));
        return BoardDto.from(board);
    }

    //내가 작성한 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<ViewListOfMyArticlesWrittenResponse> viewListOfMyArticlesWritten(SessionUser sessionUser) {
        List<Board> boards = boardRepository.findAllByWriter(sessionUser);
        return boards.stream() //1.데이터 흐름 준비단계
                .map(ViewListOfMyArticlesWrittenResponse::from) //2.중간 연산 등록단계
                .collect(Collectors.toList());//3.최종 연산단계: 결과를 리스트로 반환을 받겠다.
    }

}