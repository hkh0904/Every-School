package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.consultservice.domain.consult.QConsult.consult;

@Repository
public class ConsultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Consult> findByTeacherIdAndSchoolYear(Long teacherId, int schoolYear) {
        return queryFactory
            .select(consult)
            .from(consult)
            .where(
                consult.isDeleted.isFalse(),
                consult.teacherId.eq(teacherId),
                consult.schoolYear.eq(schoolYear),
                consult.progressStatusId.eq(ProgressStatus.WAIT.getCode())
            )
            .orderBy(consult.consultDateTime.desc())
            .fetch();
    }

    public List<Consult> findByParentId(Long userId, char userType) {
        return queryFactory
            .select(consult)
            .from(consult)
            .where(filterUserType(userId, userType))
            .orderBy(consult.createdDate.desc())
            .fetch();
    }

    private BooleanExpression filterUserType(Long userId, char userType) {
        return userType == 'T' ? consult.teacherId.eq(userId) : consult.parentId.eq(userId);
    }
}
