package com.okbo_projects.common.entity;

import com.okbo_projects.common.utils.Team;
import com.okbo_projects.domain.board.model.request.UpdateBoardRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 225)
    private String content;

    @Enumerated(EnumType.STRING)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    public Board(String title, String content, Team team, User writer) {
        this.title = title;
        this.content = content;
        this.team = team;
        this.writer = writer;
    }

    public void update(UpdateBoardRequest request) {
    }
}