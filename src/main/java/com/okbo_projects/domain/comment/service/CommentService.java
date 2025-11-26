package com.okbo_projects.domain.comment.service;

import com.okbo_projects.domain.board.repository.BoardRepository;
import com.okbo_projects.domain.comment.repository.CommentRepository;
import com.okbo_projects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


}
