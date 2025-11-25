package com.okbo_projects.common.entity;

import com.okbo_projects.common.utils.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean activated;

    public User(String nickname, String email, String password, Team team) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.team = team;
        this.activated = true;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String updatePassword) {
        this.password = updatePassword;
    }

    public void deactivate() {
        this.activated = false;
    }
}
