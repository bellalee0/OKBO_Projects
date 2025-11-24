package com.okbo_projects.domain.board.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.exception.ErrorMessage;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.dto.BoardDto;
import com.okbo_projects.domain.board.model.request.CreateBoardRequest;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardReadAllPageResponse> getBoardAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(i -> BoardReadAllPageResponse.from(BoardDto.from(i)));
    }

    // 게시글 구단별 전체 조회
    @Transactional(readOnly = true)
    public Page<BaordReadTeamPageResponse> getBoardTeamAllPage(int page, int size, String teamName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Team team = Team.valueOf(teamName);
        Page<Board> boardPage = boardRepository.findByTeam(team, pageable);
        return boardPage.map(i -> BaordReadTeamPageResponse.from(BoardDto.from(i)));
    }

//    // 팔로워 게시글 전체 조회
//    @Transactional(readOnly = true)
//    public Page<BoardReadFollowPageResponse> getBoardFollowAllPage(int page, int size, Long userId) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<Board> boardPage = boardRepository.findByFollowerBoard(userId, pageable);
//        return boardPage.map(i -> BoardReadFollowPageResponse.from(BoardDto.from(i)));
//    }

    // 게시글 삭제
    public void deleteBoard(Long userId, Long boardId) {
        Board board = findByBoardId(boardId);
        matchedWriter(userId, board.getWriter().getId());
        boardRepository.delete(board);
    }

    // 회원 확인
    private User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
    }

    // 게시물 확인
    private Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_BOARD)
        );
    }

    // 작성자 일치 확인
    private void matchedWriter(Long userId, Long boardUserId) {
        if(!userId.equals(boardUserId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN_ONLY_WRITER);
        }
    }
}