package com.everyschool.boardservice.domain.board;


import com.everyschool.boardservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer codeId;

    @Column(nullable = false, updatable = false)
    private Long schoolId;

    @Column(updatable = false)
    private Long schoolClassId;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer hit;

    @Column(nullable = false)
    private Boolean isUsedComment;

    @Column(nullable = false)
    private Integer commentPeopleNum;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachedFile> files;

    protected Board() {
        super();
        this.hit = 0;
        this.commentPeopleNum = 0;
        this.files = new ArrayList<>();
    }

    @Builder
    private Board(Integer codeId, Long schoolId, Long schoolClassId, Long userId, String title, String content, Boolean isUsedComment) {
        this();
        this.codeId = codeId;
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isUsedComment = isUsedComment;
    }
}
