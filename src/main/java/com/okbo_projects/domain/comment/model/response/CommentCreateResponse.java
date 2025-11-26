package com.okbo_projects.domain.comment.model.response;

import com.okbo_projects.domain.comment.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateResponse {
    private long commentId;
    private long boardId;
    private String comments;
    private String writer;

    public static CommentCreateResponse from(CommentDto commentDto) {
        return new CommentCreateResponse(
                commentDto.getId(),
                commentDto.getBoardId(),
                commentDto.getComments(),
                commentDto.getWriter()
        );
    }
}
