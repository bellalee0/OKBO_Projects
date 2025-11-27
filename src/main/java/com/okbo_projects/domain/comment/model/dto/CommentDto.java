package com.okbo_projects.domain.comment.model.dto;

import com.okbo_projects.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String comments;
    private String writer;
    private Long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComments(),
                comment.getWriter().getNickname(),
                comment.getBoard().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}