package com.okbo_projects.domain.board.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Comment;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.model.dto.BoardDto;
import com.okbo_projects.domain.board.model.request.BoardCreateRequest;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
import com.okbo_projects.domain.board.model.response.*;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.model.dto.CommentDto;
import com.okbo_projects.domain.comment.model.response.CommentGetAllResponse;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.follow.repository.FollowRepository;
import com.okbo_projects.domain.like.repository.LikeRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.okbo_projects.common.exception.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

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
    public BoardDetailedInquiryResponse detailedInquiryBoard(Long boardId, Pageable pageable) {
        Board board = findByBoardId(boardId);
        Page<Comment> comments = commentRepository.findByBoard_Id(board.getId(), pageable);
        Slice<CommentGetAllResponse> commentList = comments.map(i -> CommentGetAllResponse.from(CommentDto.from(i)));
        return BoardDetailedInquiryResponse.from(BoardDto.from(board), commentList);
    }

    //내가 작성한 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<BoardGetMyArticlesResponse> viewListOfMyArticlesWritten(
            SessionUser sessionUser,
            String title,
            String startDate,
            String endDate,
            Pageable pageable
    ) {
        User user = findByUserId(sessionUser.getUserId());
        boolean searchCondition = mySearchCondition(title, startDate, endDate);
        Page<Board> boardPage;
        if (searchCondition) {
            LocalDateTime startDateTime = convertToStartDateTime(startDate);
            LocalDateTime endDateTime = convertToEndDateTime(endDate);
            boardPage = boardRepository.searchMyBoards(user.getId(),title , startDateTime, endDateTime, pageable);
        } else {
            boardPage = boardRepository.findByWriter(user, pageable);
        }
        return boardPage.map(board -> BoardGetMyArticlesResponse.from(BoardDto.from(board)));
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetAllPageResponse> getBoardAllPage(
            String title,
            String writer,
            String startDate,
            String endDate,
            Pageable pageable
    ) {
        boolean searchCondition = searchCondition(title, writer, startDate, endDate);
        Page<Board> boardPage;
        if (searchCondition) {
            LocalDateTime startDateTime = convertToStartDateTime(startDate);
            LocalDateTime endDateTime = convertToEndDateTime(endDate);
            boardPage = boardRepository.searchAllBoards(title, writer, startDateTime, endDateTime, pageable);
        } else {
            boardPage = boardRepository.findAll(pageable);
        }
        return boardPage.map(board -> BoardGetAllPageResponse.from(BoardDto.from(board)));
    }

    // 게시글 구단별 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetTeamPageResponse> getBoardTeamAllPage(
            String teamName,
            String title,
            String writer,
            String startDate,
            String endDate,
            Pageable pageable
    ) {
        Team team = Team.valueOf(teamName);
        boolean searchCondition = searchCondition(title, writer, startDate, endDate);
        Page<Board> boardPage;
        if (searchCondition) {
            LocalDateTime startDateTime = convertToStartDateTime(startDate);
            LocalDateTime endDateTime = convertToEndDateTime(endDate);
            boardPage = boardRepository.searchTeamBoards(team, title, writer, startDateTime, endDateTime, pageable);
        } else {
            boardPage = boardRepository.findByTeam(team, pageable);
        }
        return boardPage.map(board -> BoardGetTeamPageResponse.from(BoardDto.from(board)));
    }

    // 팔로워 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardGetFollowPageResponse> getBoardFollowAllPage(
            SessionUser sessionUser,
            String title,
            String writer,
            String startDate,
            String endDate,
            Pageable pageable
    ) {
        boolean searchCondition = searchCondition(title, writer, startDate, endDate);
        User user = findByUserId(sessionUser.getUserId());
        findByFromUser(user);
        Page<Board> boardPage;
        if (searchCondition) {
            LocalDateTime startDateTime = convertToStartDateTime(startDate);
            LocalDateTime endDateTime = convertToEndDateTime(endDate);
            boardPage = boardRepository.searchByFollowerBoard(user.getId(), title, writer, startDateTime, endDateTime, pageable);
        } else {
            boardPage = boardRepository.findByFollowerBoard(user.getId(), pageable);
        }
        return boardPage.map(board -> BoardGetFollowPageResponse.from(BoardDto.from(board)));
    }

    // check condition nullable()
    private boolean searchCondition(String title, String writer, String startDate, String endDate) {
        return (title != null && !title.isBlank()) ||
                (writer != null && !writer.isBlank()) ||
                (startDate != null) ||
                (endDate != null);
    }

    // check condition nullable(내 게시글 조회)
    private boolean mySearchCondition(String title, String startDate, String endDate) {
        return (title != null && !title.isBlank()) ||
                (startDate != null) ||
                (endDate != null);
    }

    // startDate 날짜 포맷 변환
    private LocalDateTime convertToStartDateTime(String startDate) {
        if(startDate != null) {
            return LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
        }
        return null;
    }

    // endDate 날짜 포맷 변환
    private LocalDateTime convertToEndDateTime(String endDate) {
        if(endDate != null) {
            return LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(23, 59, 59);
        }
        return null;
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
        likeRepository.deleteByBoard(board);
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