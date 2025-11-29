package com.okbo_projects.domain.comment.model.dto;

import com.okbo_projects.common.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long boardId;
    private String comments;
    private String writer;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getBoard().getId(),
            comment.getComments(),
            comment.getWriter().getNickname(),
            comment.getLikes(),
            comment.getCreatedAt(),
            comment.getModifiedAt()
        );
    }
}