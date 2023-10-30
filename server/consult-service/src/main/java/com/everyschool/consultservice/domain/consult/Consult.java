package com.everyschool.consultservice.domain.consult;

import com.everyschool.consultservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Consult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consult_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime consultDateTime;

    @Column(nullable = false, length = 500)
    private String message;

    @Lob
    @Column(insertable = false)
    private String resultContent;

    @Column(insertable = false, length = 50)
    private String rejectedReason;

    @Embedded
    private Title title;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false)
    private Integer progressStatusId;

    @Column(nullable = false, updatable = false)
    private Integer typeId;

    @Column(nullable = false, updatable = false)
    private Long schoolId;

    @Column(nullable = false, updatable = false)
    private Long parentId;

    @Column(nullable = false, updatable = false)
    private Long studentId;

    @Column(nullable = false, updatable = false)
    private Long teacherId;

    protected Consult() {
        super();
    }

    @Builder
    private Consult(LocalDateTime consultDateTime, String message, Title title, Integer schoolYear, Integer progressStatusId, Integer typeId, Long schoolId, Long parentId, Long studentId, Long teacherId) {
        this();
        this.consultDateTime = consultDateTime;
        this.message = message;
        this.title = title;
        this.schoolYear = schoolYear;
        this.progressStatusId = progressStatusId;
        this.typeId = typeId;
        this.schoolId = schoolId;
        this.parentId = parentId;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }
}

