package com.okbo_projects.domain.follow.service;

import com.okbo_projects.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {
    private final FollowRepository followRepository;
}
