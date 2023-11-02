package com.everyschool.schoolservice.domain.schooluser;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_user_id")
    private Long id;

    @Column(updatable = false)
    private Integer studentNumber;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false, updatable = false)
    private Integer userTypeId;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Builder
    private SchoolUser(Integer studentNumber, Integer schoolYear, Integer userTypeId, Long userId, School school, SchoolClass schoolClass) {
        super();
        this.studentNumber = studentNumber;
        this.schoolYear = schoolYear;
        this.userTypeId = userTypeId;
        this.userId = userId;
        this.school = school;
        this.schoolClass = schoolClass;
    }
}
