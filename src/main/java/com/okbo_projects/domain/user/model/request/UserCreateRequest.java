package com.okbo_projects.domain.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank
    @Size(max = 10, message = "닉네임은 10글자를 넘길 수 없습니다.")
    private String nickname;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String favoriteTeam;
}