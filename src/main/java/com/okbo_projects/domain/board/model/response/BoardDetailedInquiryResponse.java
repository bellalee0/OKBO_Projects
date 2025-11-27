package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import com.okbo_projects.domain.comment.model.response.CommentGetAllResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailedInquiryResponse {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String team;
    private Long comments;
    private Long likes;
    private LocalDateTime creatAt;
    private LocalDateTime modifiedAt;
    private Slice<CommentGetAllResponse> commentList;

    public static BoardDetailedInquiryResponse from(BoardDto dto, Slice<CommentGetAllResponse> commentList) {
        return new BoardDetailedInquiryResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getWriter(),
                dto.getTeam().getTeamName(),
                dto.getComments(),
                dto.getLikes(),
                dto.getCreatedAt(),
                dto.getModifiedAt(),
                commentList
        );
    }
}