package com.everyschool.schoolservice.domain.schoolapply;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolApply extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_apply_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false, updatable = false, length = 30)
    private String studentInfo;

    @Column(nullable = false)
    private Boolean isApproved;

    @Column(insertable = false, length = 50)
    private String rejectedReason;

    @Column(nullable = false, updatable = false)
    private Integer codeId;

    @Column(nullable = false, updatable = false)
    private Long studentId;

    @Column(updatable = false)
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Builder
    private SchoolApply(Integer schoolYear, String studentInfo, Integer codeId, Long studentId, Long parentId, SchoolClass schoolClass) {
        super();
        this.schoolYear = schoolYear;
        this.studentInfo = studentInfo;
        this.codeId = codeId;
        this.studentId = studentId;
        this.parentId = parentId;
        this.schoolClass = schoolClass;
        this.isApproved = false;
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
