package com.everyschool.userservice.domain.user;

import com.everyschool.userservice.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(StudentParentId.class)
public class StudentParent extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
