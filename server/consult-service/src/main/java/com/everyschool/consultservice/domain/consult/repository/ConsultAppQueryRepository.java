package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.api.app.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.everyschool.consultservice.domain.consult.QConsult.*;

/**
 * 상담 앱 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class ConsultAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 학부모 상담 목록 조회
     *
     * @param parentId   학부모 아이디
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 목록
     */
    public List<ConsultResponse> findByParentId(Long parentId, int schoolYear, Long schoolId) {
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
                consult.schoolId.eq(schoolId),
                consult.parentId.eq(parentId)
            )
            .orderBy(consult.lastModifiedDate.desc())
            .fetch();
    }

    /**
     * 교직원 상담 목록 조회
     *
     * @param teacherId  교직원 아이디
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 목록
     */
    public List<ConsultResponse> findByTeacherId(Long teacherId, int schoolYear, Long schoolId) {
        return queryFactory
            .select(
                Projections.constructor(
                    ConsultResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.parentTitle,
                    consult.consultDateTime
                )
            )
            .from(consult)
            .where(
                consult.isDeleted.isFalse(),
                consult.schoolYear.eq(schoolYear),
                consult.schoolId.eq(schoolId),
                consult.teacherId.eq(teacherId)
            )
            .orderBy(consult.lastModifiedDate.desc())
            .fetch();
    }

    /**
     * 상담 상세 조회
     *
     * @param consultId 상담 아이디
     * @return 조회된 상담 상세 정보
     */
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
