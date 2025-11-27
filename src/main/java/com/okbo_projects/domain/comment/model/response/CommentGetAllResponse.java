package com.okbo_projects.domain.comment.model.response;

import com.okbo_projects.domain.comment.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetAllResponse {

    private Long commentId;
    private String comments;
    private String writer;

    public static CommentGetAllResponse from(CommentDto commentDto) {
        return new CommentGetAllResponse(
                commentDto.getId(),
                commentDto.getComments(),
                commentDto.getWriter()
        );
    }
}