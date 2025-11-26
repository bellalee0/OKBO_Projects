package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetAllPageResponse {
    private Long id;
    private String title;
    private String team;
    private String writer;

    public static BoardGetAllPageResponse from(BoardDto boardDto) {
        return new BoardGetAllPageResponse(
                boardDto.getId(),
                boardDto.getTitle(),
                boardDto.getTeam().getTeamName(),
                boardDto.getWriter()
        );
    }
}