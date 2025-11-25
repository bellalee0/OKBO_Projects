package com.okbo_projects.domain.board.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequest {

    @NotBlank(message = "제목을 작성해주세요.")
    @Size(message = "30자 이하로 작성해주세요.")
    private String title;

    @NotBlank(message = "내용을 작성해주세요.")
    @Size(message = "225자 이하로 작성해주세요.")
    private String content;

    @NotBlank(message = "팀을 작성해주세요.")
    private String team;
}