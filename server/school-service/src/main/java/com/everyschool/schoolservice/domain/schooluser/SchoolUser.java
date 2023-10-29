package com.everyschool.schoolservice.domain.schooluser;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SchoolUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_user_id")
    private Long id;

    @Column(nullable = false)
    private Integer schoolUserCodeId;

    @Column(nullable = false)
    private Integer schoolYear;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    protected SchoolUser() {
        super();
    }

    @Builder
    private SchoolUser(Integer schoolUserCodeId, Integer schoolYear, Long userId, School school, SchoolClass schoolClass) {
        this.schoolUserCodeId = schoolUserCodeId;
        this.schoolYear = schoolYear;
        this.userId = userId;
        this.school = school;
        this.schoolClass = schoolClass;
    }
}
