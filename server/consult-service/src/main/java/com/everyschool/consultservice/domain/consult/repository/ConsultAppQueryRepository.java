package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.api.app.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.QConsult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.everyschool.consultservice.domain.consult.QConsult.*;

@Repository
public class ConsultAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ConsultResponse> findByParentIdAndSchoolYear(Long parentId, int schoolYear) {
        return queryFactory
            .select(
                Projections.constructor(
                    ConsultResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.teacherTitle,
                    consult.consultDateTime
                )
            )
            .from(consult)
            .where(
                consult.isDeleted.isFalse(),
                consult.schoolYear.eq(schoolYear),
                consult.parentId.eq(parentId)
            )
            .orderBy(consult.lastModifiedDate.desc())
            .fetch();
    }

    public List<ConsultResponse> findByTeacherIdAndSchoolYear(Long teacherId, Integer schoolYear) {
        return queryFactory
            .select(
                Projections.constructor(
                    ConsultResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.teacherTitle,
                    consult.consultDateTime
                )
            )
            .from(consult)
            .where(
                consult.isDeleted.isFalse(),
                consult.schoolYear.eq(schoolYear),
                consult.teacherId.eq(teacherId)
            )
            .orderBy(consult.lastModifiedDate.desc())
            .fetch();
    }

    public Optional<ConsultDetailResponse> findById(Long consultId) {
        ConsultDetailResponse content = queryFactory
            .select(
                Projections.constructor(
                    ConsultDetailResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.teacherTitle,
                    consult.title.parentTitle,
                    consult.consultDateTime,
                    consult.message,
                    consult.resultContent,
                    consult.rejectedReason
                )
            )
            .from(consult)
            .where(
                consult.id.eq(consultId)
            )
            .fetchOne();
        return Optional.ofNullable(content);
    }
}
