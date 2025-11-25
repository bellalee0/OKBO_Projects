package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.utils.Team;
import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardReadFollowPageResponse {

    private Long id;
    private String title;
    private String team;
    private String writer;

    public static BoardReadFollowPageResponse from(BoardDto boardDto) {
        return new BoardReadFollowPageResponse(
                boardDto.getId(),
                boardDto.getTitle(),
                boardDto.getTeam().getTeamName(),
                boardDto.getWriter()
        );
    }
}
