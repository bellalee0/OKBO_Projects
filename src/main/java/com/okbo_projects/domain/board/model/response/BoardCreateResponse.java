package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateResponse {
    private Long id;
    private String title;
    private String content;
    private String team;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardCreateResponse from(BoardDto dto) {
        return new BoardCreateResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getTeam().getTeamName(),
                dto.getContent(),
                dto.getWriter(),
                dto.getCreatedAt(),
                dto.getModifiedAt()
        );
    }
}