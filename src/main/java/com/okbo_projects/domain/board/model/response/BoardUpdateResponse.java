package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateResponse {
    private Long id;
    private String title;
    private String content;
    private String team;

    public static BoardUpdateResponse from(BoardDto dto) {
        return new BoardUpdateResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getTeam().getTeamName()
        );
    }
}