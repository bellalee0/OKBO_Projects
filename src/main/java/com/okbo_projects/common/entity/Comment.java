package com.okbo_projects.common.entity;

import com.okbo_projects.domain.comment.model.dto.CommentDto;
import com.okbo_projects.domain.comment.model.request.CommentUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 225)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Comment(String comments, User writer, Board board) {
        this.comments = comments;
        this.writer = writer;
        this.board = board;
    }


    public void update(CommentUpdateRequest request) {
        this.comments = request.getComments();
    }

    public CommentDto toDto() {
        return CommentDto.from(this);
    }
}
