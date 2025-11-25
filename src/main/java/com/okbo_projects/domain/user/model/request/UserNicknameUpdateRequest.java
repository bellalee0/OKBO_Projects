package com.okbo_projects.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNicknameUpdateRequest {
    @NotBlank
    @Size(max = 10, message = "닉네임은 10글자를 넘길 수 없습니다.")
    private String nickname;
}