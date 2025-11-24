package com.okbo_projects.domain.board.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.common.utils.Team;
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

import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_BOARD;
import static com.okbo_projects.common.exception.ErrorMessage.NOT_FOUND_USER;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 생성
    public CreateBoardResponse createBoard(SessionUser sessionUser,Long writer, CreateBoardRequest request) {
        //1.유저의 아이디를 검색하여 없으면 예외처리
        User user = userRepository.findById(writer).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER));

        if(!sessionUser.getId().equals(user.getId())) {
            throw new CustomException(NOT_FOUND_USER);
        }
        Team team = Team.valueOf((request.getTeam()));

        //2.게시글 생성
        Board board = new Board(
                request.getTitle(),
                request.getContent(),
                team,
                user
        );
        //3.게시글 저장
        boardRepository.save(board);
        BoardDto dto = BoardDto.from(board);

        return CreateBoardResponse.from(dto);
    }

    //게시글 수정
    public UpdateBoardResponse updateBoard(SessionUser sessionUser,Long writer, UpdateBoardRequest request) {
        //1.게시글의 아이디를 검색하여 없으면 예외처리
        Board board = boardRepository.findById(writer).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD));

        if(!sessionUser.getId().equals(board.getWriter().getId())) {
            throw new CustomException(NOT_FOUND_USER);
        }
        //2.게시글 수정
        board.update(request);
        //3.게시글 저장
        boardRepository.save(board);
        //4.게시글 반환
        BoardDto dto = BoardDto.from(board);
        return UpdateBoardResponse.from(dto);

    }
    //게시글 상세조회
    @Transactional(readOnly = true)
    public BoardDto detailedInquiryBoard(Long id) {
        //1.게시글의 아이디를 검색하여 없으면 예외처리
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD));
        //2.게시글 반환
        return BoardDto.from(board);
    }

    //내가 작성한 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<ViewListOfMyArticlesWrittenResponse> viewListOfMyArticlesWritten(SessionUser sessionUser) {
        //1.유저의 아이디를 검색하여 없으면 예외처리
        User user = userRepository.findById(sessionUser.getUserId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER));
        //2.내가 작성한 게시글 목록 조회
        List<Board> boards = boardRepository.findByWriter(user);
        //3.내가 작성한 게시글 목록 반환
        return boards.stream() //1.데이터 흐름 준비단계
                .map(ViewListOfMyArticlesWrittenResponse::from) //2.중간 연산 등록단계
                .collect(Collectors.toList());//3.최종 연산단계: 결과를 리스트로 반환을 받겠다.
    }

}