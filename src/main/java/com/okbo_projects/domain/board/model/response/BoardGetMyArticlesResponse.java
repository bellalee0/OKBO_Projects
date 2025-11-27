package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetMyArticlesResponse {

    private Long id;
    private String title;
    private String content;
    private String team;
    private Long comments;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardGetMyArticlesResponse from(BoardDto boardDto) {
        return new BoardGetMyArticlesResponse(
                boardDto.getId(),
                boardDto.getTitle(),
                boardDto.getContent(),
                boardDto.getTeam().getTeamName(),
                boardDto.getComments(),
                boardDto.getLikes(),
                boardDto.getCreatedAt(),
                boardDto.getModifiedAt()
        );
    }
}