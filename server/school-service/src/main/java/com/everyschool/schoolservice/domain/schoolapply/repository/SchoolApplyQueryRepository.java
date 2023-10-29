package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    private BooleanExpression statusCond(String status) {
        if (status.equals("wait")) {
            return schoolApply.isApproved.isFalse().and(schoolApply.rejectedReason.isNull());
        }
        return schoolApply.isApproved.isTrue();
    }
}
