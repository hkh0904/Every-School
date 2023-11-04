package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.api.controller.client.response.StudentParentInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.userservice.domain.user.QParent.parent;
import static com.everyschool.userservice.domain.user.QStudent.student;
import static com.everyschool.userservice.domain.user.QStudentParent.studentParent;

@Repository
public class StudentParentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudentParentQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StudentParentInfo> findBySchoolClassId(Long schoolClassId) {
        return queryFactory
            .select(
                Projections.constructor(
                    StudentParentInfo.class,
                    studentParent.student.id,
                    studentParent.parent.id,
                    studentParent.parent.parentType
                ))
            .from(studentParent)
            .join(studentParent.parent, parent)
            .join(studentParent.student, student)
            .where(
                studentParent.isDeleted.isFalse(),
                studentParent.student.schoolClassId.eq(schoolClassId)
            )
            .fetch();
    }
}
