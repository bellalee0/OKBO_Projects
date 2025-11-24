package com.okbo_projects.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;
}
