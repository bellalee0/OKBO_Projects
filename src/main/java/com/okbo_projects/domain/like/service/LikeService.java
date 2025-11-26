package com.okbo_projects.domain.like.service;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.entity.Like;
import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.model.SessionUser;
import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.like.repository.LikeRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 게시글 좋아요 추가
    public void addBoardLike(Long boardId, SessionUser sessionUser) {

        Board board = boardRepository.findBoardById(boardId);
        User user = userRepository.findUserById(sessionUser.getUserId());

        Like like = new Like(
                user,
                board
        );

        likeRepository.save(like);
    }
}
