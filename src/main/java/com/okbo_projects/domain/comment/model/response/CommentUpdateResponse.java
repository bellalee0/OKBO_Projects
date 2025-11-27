package com.okbo_projects.domain.comment.model.response;

import com.okbo_projects.domain.comment.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponse {

    private Long commentId;
    private Long boardId;
    private String comments;
    private String writer;
    private LocalDateTime creatAt;
    private LocalDateTime modifiedAt;

    public static CommentUpdateResponse from(CommentDto dto) {
        return new CommentUpdateResponse(
                dto.getId(),
                dto.getBoardId(),
                dto.getComments(),
                dto.getWriter(),
                dto.getCreatedAt(),
                dto.getModifiedAt()
        );
    }
}