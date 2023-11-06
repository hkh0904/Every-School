package com.everyschool.schoolservice.domain.schoolapply;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SchoolApply extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_apply_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false)
    private Boolean isApproved;

    @Column(insertable = false, length = 50)
    private String rejectedReason;

    @Column(nullable = false)
    private Integer codeId;

    private Long studentId;

    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    protected SchoolApply() {
        super();
        this.isApproved = false;
    }

    @Builder
    private SchoolApply(Integer schoolYear, Integer codeId, Long studentId, Long parentId, SchoolClass schoolClass) {
        this();
        this.schoolYear = schoolYear;
        this.codeId = codeId;
        this.studentId = studentId;
        this.parentId = parentId;
        this.schoolClass = schoolClass;
    }

    public SchoolApply approve() {
        this.isApproved = true;
        return this;
    }

    public SchoolApply reject(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }
}
