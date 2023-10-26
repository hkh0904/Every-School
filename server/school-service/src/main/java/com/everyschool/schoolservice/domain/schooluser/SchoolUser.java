package com.everyschool.schoolservice.domain.schooluser;

import com.everyschool.schoolservice.domain.BaseEntity;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "`school_user`")
public class SchoolUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int schoolYear;

    @Column(nullable = false)
    private int codeId;

    protected SchoolUser() {
        super();
    }

    @Builder
    public SchoolUser(School school, SchoolClass schoolClass, Long userId, int schoolYear, int codeId) {
        this();
        this.school = school;
        this.schoolClass = schoolClass;
        this.userId = userId;
        this.schoolYear = schoolYear;
        this.codeId = codeId;
    }
}
