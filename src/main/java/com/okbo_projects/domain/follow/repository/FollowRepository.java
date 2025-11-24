package com.okbo_projects.domain.follow.repository;

import com.okbo_projects.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
