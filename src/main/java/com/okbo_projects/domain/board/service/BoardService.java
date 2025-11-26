package com.okbo_projects.domain.board.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.dto.BoardDto;
import com.okbo_projects.domain.board.model.request.BoardCreateRequest;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.follow.repository.FollowRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.okbo_projects.common.exception.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    //게시글 생성
    public BoardCreateResponse createBoard(SessionUser sessionUser, BoardCreateRequest request) {
        User user = findByUserId(sessionUser.getUserId());
        Team team = Team.valueOf((request.getTeam()));
        Board board = new Board(
                request.getTitle(),
                request.getContent(),
                team,
                user
        );
        boardRepository.save(board);
        BoardDto dto = BoardDto.from(board);
        return BoardCreateResponse.from(dto);
    }

    //게시글 수정
    public BoardUpdateResponse updateBoard(SessionUser sessionUser, Long boardId, BoardUpdateRequest request) {
        Board board = findByBoardId(boardId);
        matchedWriter(sessionUser.getUserId(), board.getWriter().getId());
        board.update(request);
        boardRepository.save(board);
        BoardDto dto = BoardDto.from(board);
        return BoardUpdateResponse.from(dto);
    }

    //게시글 상세조회
    @Transactional(readOnly = true)
    public BoardDto detailedInquiryBoard(Long boardId) {
        Board board = findByBoardId(boardId);
        return BoardDto.from(board);
    }

    //내가 작성한 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<BoardGetMyArticlesResponse> viewListOfMyArticlesWritten(SessionUser sessionUser, int page, int size) {
        User user = findByUserId(sessionUser.getUserId());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Board> boardPage = boardRepository.findByWriter(user, pageable);
        return boardPage.map(BoardGetMyArticlesResponse::from);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetAllPageResponse> getBoardAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(i -> BoardGetAllPageResponse.from(BoardDto.from(i)));
    }

    // 게시글 구단별 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetTeamPageResponse> getBoardTeamAllPage(int page, int size, String teamName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Team team = Team.valueOf(teamName);
        Page<Board> boardPage = boardRepository.findByTeam(team, pageable);
        return boardPage.map(i -> BoardGetTeamPageResponse.from(BoardDto.from(i)));
    }

    // 팔로워 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetFollowPageResponse> getBoardFollowAllPage(int page, int size, SessionUser sessionUser) {
        User user = findByUserId(sessionUser.getUserId());
        findByFromUser(user);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Board> boardPage = boardRepository.findByFollowerBoard(user.getId(), pageable);
        return boardPage.map(i -> BoardGetFollowPageResponse.from(BoardDto.from(i)));
    }

    // 팔로워 확인
    private void findByFromUser(User user) {
        boolean existsFollowingList = followRepository.existsByFromUser(user);
        if (!existsFollowingList) {
            throw new CustomException(NOT_FOUND_FOLLOWING);
        }
    }

    // 게시글 삭제
    public void deleteBoard(SessionUser sessionUser, Long boardId) {
        Board board = findByBoardId(boardId);
        matchedWriter(sessionUser.getUserId(), board.getWriter().getId());
        boardRepository.delete(board);
    }

    // 회원 확인
    private User findByUserId(Long userId) {
        return userRepository.findUserById(userId);
    }

    // 게시물 확인
    private Board findByBoardId(Long boardId) {
        return boardRepository.findBoardById(boardId);
    }

    // 작성자 일치 확인
    private void matchedWriter(Long userId, Long boardUserId) {
        if(!userId.equals(boardUserId)) {
            throw new CustomException(FORBIDDEN_ONLY_WRITER);
        }
    }
}