package com.everyschool.schoolservice.domain.schoolclass;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.school.School;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SchoolClass extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_class_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false, updatable = false)
    private Integer grade;

    @Column(nullable = false, updatable = false)
    private Integer classNum;

    @Column(updatable = false, length = 10)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false, length = 6)
    private Long teacherId;

    protected SchoolClass() {
        super();
    }

    @Builder
    private SchoolClass(Integer schoolYear, Integer grade, Integer classNum, String name, School school, Long teacherId) {
        this();
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
        this.name = name;
        this.school = school;
        this.teacherId = teacherId;
    }
}
