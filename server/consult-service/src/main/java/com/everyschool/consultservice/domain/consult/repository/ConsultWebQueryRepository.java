package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.api.web.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.everyschool.consultservice.domain.consult.QConsult.consult;

/**
 * 상담 웹 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class ConsultWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 상담 목록 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param teacherId  교직원 아이디
     * @param status     상담 진행 상태 코드
     * @return 조회된 상담 목록 리스트
     */
    public List<ConsultResponse> findByTeacherId(int schoolYear, Long schoolId, Long teacherId, int status) {
        return queryFactory
            .select(
                Projections.constructor(
                    ConsultResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.parentTitle,
                    consult.lastModifiedDate,
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

    /**
     * 상담 상세 조회
     *
     * @param consultId 상담 아이디
     * @return 조회된 상담 상세 내용
     */
    public Optional<ConsultDetailResponse> findById(Long consultId) {
        ConsultDetailResponse content = queryFactory
            .select(
                Projections.constructor(
                    ConsultDetailResponse.class,
                    consult.id,
                    consult.typeId,
                    consult.progressStatusId,
                    consult.title.parentTitle,
                    consult.consultDateTime,
                    consult.message,
                    consult.resultContent,
                    consult.rejectedReason,
                    consult.lastModifiedDate
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
