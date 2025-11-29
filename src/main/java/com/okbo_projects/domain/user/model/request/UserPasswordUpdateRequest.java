package com.okbo_projects.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordUpdateRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
