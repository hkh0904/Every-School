package com.everyschool.schoolservice.domain.application;

import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "`school_apply`")
public class SchoolApply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Column(nullable = false)
    private Long studentId;

    private int parentId;

    @Column(nullable = false)
    private int schoolYear;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isApproved;

    @Column(length = 50)
    private String rejectedReason;

    @Column(nullable = false)
    private int codeId;

    protected SchoolApply() {
        super();
    }

    @Builder

    public SchoolApply(SchoolClass schoolClass, Long studentId, int parentId, int schoolYear, boolean isApproved,
                       String rejectedReason, int codeId) {
        this();
        this.schoolClass = schoolClass;
        this.studentId = studentId;
        this.parentId = parentId;
        this.schoolYear = schoolYear;
        this.isApproved = isApproved;
        this.rejectedReason = rejectedReason;
        this.codeId = codeId;
    }
}
