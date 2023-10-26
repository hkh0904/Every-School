package com.everyschool.schoolservice.domain.schoolclass;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.school.School;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "`school_class`")
public class SchoolClass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_class_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false, length = 6)
    private Long teacherId;

    @Column(nullable = false)
    private int schoolYear;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private int classNum;

    @Column(length = 10)
    private String name;

    protected SchoolClass() {
        super();
    }

    @Builder
    public SchoolClass(School school, Long teacherId, int schoolYear, int grade, int classNum, String name) {
        this();
        this.school = school;
        this.teacherId = teacherId;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
        this.name = name;
    }
}
