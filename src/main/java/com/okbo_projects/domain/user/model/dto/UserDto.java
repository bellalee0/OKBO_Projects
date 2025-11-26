package com.okbo_projects.domain.user.model.dto;

import com.okbo_projects.common.entity.User;
import com.okbo_projects.common.utils.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private String email;
    private Team team;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getTeam(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}