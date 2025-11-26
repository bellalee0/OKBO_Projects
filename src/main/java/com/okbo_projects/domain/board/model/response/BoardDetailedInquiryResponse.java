package com.okbo_projects.domain.board.model.response;

import com.okbo_projects.domain.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailedInquiryResponse {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String team;
    private LocalDateTime creatAt;
    private LocalDateTime modifiedAt;

    public static BoardDetailedInquiryResponse from(BoardDto dto) {
        return new BoardDetailedInquiryResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getWriter(),
                dto.getTeam().getTeamName(),
                dto.getCreatedAt(),
                dto.getModifiedAt()
        );
    }
}