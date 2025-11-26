package com.okbo_projects.domain.like.repository;

import com.okbo_projects.common.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
