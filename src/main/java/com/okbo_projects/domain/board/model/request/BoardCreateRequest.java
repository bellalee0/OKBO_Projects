package com.okbo_projects.domain.board.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "제목을 작성해주세요.")
    @Size(max = 30, message = "30자 이하로 작성해주세요.")
    private String title;

    @NotBlank(message = "팀을 작성해주세요.")
    private String team;

    @NotBlank(message = "내용을 작성해주세요.")
    @Size(max = 225, message = "225자 이하로 작성해주세요.")
    private String content;
}