package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.everyschool.schoolservice.domain.schoolapply.QSchoolApply.schoolApply;
import static com.everyschool.schoolservice.domain.schoolclass.QSchoolClass.schoolClass;

@Repository
public class SchoolApplyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolApplyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SchoolApply> findByTeacherId(Long teacherId, String status) {
        return queryFactory
                .select(schoolApply)
                .from(schoolApply)
                .join(schoolApply.schoolClass, schoolClass).fetchJoin()
                .where(
                        schoolApply.schoolClass.teacherId.eq(teacherId),
                        statusCond(status)
                )
                .orderBy(schoolApply.createdDate.desc())
                .fetch();
    }

    public Optional<SchoolApply> findById(Long schoolApplyId) {
        SchoolApply content = queryFactory
                .select(schoolApply)
                .from(schoolApply)
                .join(schoolApply.schoolClass, schoolClass).fetchJoin()
                .where(schoolApply.id.eq(schoolApplyId))
                .fetchOne();
        return Optional.ofNullable(content);
    }

    private BooleanExpression statusCond(String status) {
        if (status.equals("wait")) {
            return schoolApply.isApproved.isFalse().and(schoolApply.rejectedReason.isNull());
        }
        return schoolApply.isApproved.isTrue();
    }

    public boolean existApply(Long studentId) {
        Long content = queryFactory
                .select(schoolApply.id)
                .from(schoolApply)
                .where(
                        schoolApply.isApproved.isFalse(),
                        schoolApply.rejectedReason.isNull(),
                        schoolApply.studentId.eq(studentId)
                )
                .orderBy(schoolApply.createdDate.desc())
                .fetchOne();
        return content != null;
    }

    public boolean existApplyParent(Long parentId) {
        Long content = queryFactory
                .select(schoolApply.id)
                .from(schoolApply)
                .where(
                        schoolApply.isApproved.isFalse(),
                        schoolApply.rejectedReason.isNull(),
                        schoolApply.parentId.eq(parentId)
                )
                .orderBy(schoolApply.createdDate.desc())
                .fetchOne();
        return content != null;
    }
}
