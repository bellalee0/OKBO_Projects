package com.okbo_projects.domain.comment.repository;

import com.okbo_projects.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
