package com.okbo_projects.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteRequest {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%])(?=\\S+$).{8,15}$", message = "비밀번호는 영어와 숫자, 특수문자를 최소 1개 이상 포함해서 8~15자리 이내로 입력해주세요.")
    private String password;
}
