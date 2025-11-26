package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.common.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetMyArticlesResponse {
    private Long id;
    private String title;
    private String content;
    private String team;

    public static BoardGetMyArticlesResponse from(Board board) {
        return new BoardGetMyArticlesResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getTeam().getTeamName()
        );
    }
}