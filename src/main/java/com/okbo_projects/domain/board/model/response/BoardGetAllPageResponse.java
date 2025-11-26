package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetAllPageResponse {
    private Long id;
    private String title;
    private String team;
    private String writer;
    private Long comments;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardGetAllPageResponse from(BoardDto boardDto, Long comments, Long likes) {
        return new BoardGetAllPageResponse(
                boardDto.getId(),
                boardDto.getTitle(),
                boardDto.getTeam().getTeamName(),
                boardDto.getWriter(),
                comments,
                likes,
                boardDto.getCreatedAt(),
                boardDto.getModifiedAt()
        );
    }
}