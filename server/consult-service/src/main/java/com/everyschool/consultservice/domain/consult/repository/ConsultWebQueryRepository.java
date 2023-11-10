package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.everyschool.consultservice.domain.consult.QConsult.consult;

@Repository
public class ConsultWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ConsultResponse> findByTeacherId(int schoolYear, Long schoolId, Long teacherId, int status) {
        return queryFactory
            .select(
                Projections.constructor(
                    ConsultResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.parentTitle,
                    consult.consultDateTime,
                    consult.rejectedReason
                )
            )
            .from(consult)
            .where(
                consult.isDeleted.isFalse(),
                consult.schoolYear.eq(schoolYear),
                consult.schoolId.eq(schoolId),
                consult.teacherId.eq(teacherId),
                consult.progressStatusId.eq(status)
            )
            .orderBy(consult.lastModifiedDate.desc())
            .fetch();
    }
}
