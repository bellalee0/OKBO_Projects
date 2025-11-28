package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateResponse {

    private Long id;
    private String title;
    private String content;
    private String team;
    private String writer;
    private Long comments;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardCreateResponse from(BoardDto dto) {
        return new BoardCreateResponse(
            dto.getId(),
            dto.getTitle(),
            dto.getContent(),
            dto.getTeam().getTeamName(),
            dto.getWriter(),
            dto.getComments(),
            dto.getLikes(),
            dto.getCreatedAt(),
            dto.getModifiedAt()
        );
    }
}