package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.QParent;
import com.everyschool.userservice.domain.user.Student;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.userservice.domain.user.QParent.parent;
import static com.everyschool.userservice.domain.user.QStudent.student;
import static com.everyschool.userservice.domain.user.QStudentParent.studentParent;

@Repository
public class StudentParentAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudentParentAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Student> findByParentId(Long parentId) {
        return queryFactory
            .select(studentParent.student)
            .from(studentParent)
            .join(studentParent.student, student).fetchJoin()
            .where(studentParent.parent.id.eq(parentId))
            .fetch();
    }

    public List<Parent> findByStudentId(Long studentId) {
        return queryFactory
            .select(studentParent.parent)
            .from(studentParent)
            .join(studentParent.parent, parent).fetchJoin()
            .where(studentParent.student.id.eq(studentId))
            .fetch();
    }
}
