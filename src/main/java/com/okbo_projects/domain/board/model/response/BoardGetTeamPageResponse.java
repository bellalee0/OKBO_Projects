package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetTeamPageResponse {
    private Long id;
    private String title;
    private String writer;

    public static BoardGetTeamPageResponse from(BoardDto boardDto) {
        return new BoardGetTeamPageResponse(
                boardDto.getId(),
                boardDto.getTitle(),
                boardDto.getWriter()
        );
    }
}