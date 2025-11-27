package com.okbo_projects.domain.board.model.dto;

import com.okbo_projects.common.entity.Board;
import com.okbo_projects.common.model.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private Team team;
    private String content;
    private String writer;
    private Long comments;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardDto from(Board board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getTeam(),
                board.getContent(),
                board.getWriter().getNickname(),
                board.getComments(),
                board.getLikes(),
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }
}