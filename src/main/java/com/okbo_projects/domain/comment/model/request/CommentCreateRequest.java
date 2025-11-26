package com.okbo_projects.domain.comment.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
    @NotBlank(message = "댓글을 작성해주세요.")
    @Size(max = 225, message = "225이하만 입력 가능합니다")
    private String comments;
}
