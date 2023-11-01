package com.everyschool.boardservice.domain.board;

import com.everyschool.boardservice.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(nullable = false, updatable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private Integer depth;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    private Comment(String content, Integer depth, Long userId, Board board, Comment parent) {
        super();
        this.content = content;
        this.depth = depth;
        this.userId = userId;
        this.board = board;
        this.parent = parent;
    }
}
