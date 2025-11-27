package com.okbo_projects.common.entity;

import com.okbo_projects.common.model.Team;
import com.okbo_projects.domain.board.model.request.BoardUpdateRequest;
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

    private Long comments;
    private Long likes;

    public Board(String title, String content, Team team, User writer) {
        this.title = title;
        this.content = content;
        this.team = team;
        this.writer = writer;
        this.comments = 0L;
        this.likes = 0L;
    }

    public void update(BoardUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void addComments() {
        this.comments++;
    }
    public void addLikes() {
        this.likes++;
    }
    public void minusComments() {
        this.comments--;
    }
    public void minusLikes() {
        this.likes--;
    }
}