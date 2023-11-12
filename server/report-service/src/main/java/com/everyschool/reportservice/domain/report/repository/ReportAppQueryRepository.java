package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.reportservice.domain.report.QReport.report;

/**
 * 앱 신고 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class ReportAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원 아이디로 신고 내역 목록 조회
     *
     * @param userId     회원 아이디
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 신고 내역 목록
     */
    public List<ReportResponse> findByUserId(Long userId, int schoolYear, Long schoolId) {
        return queryFactory
            .select(
                Projections.constructor(
                    ReportResponse.class,
                    report.id,
                    report.typeId,
                    report.progressStatusId,
                    report.createdDate
                )
            )
            .from(report)
            .where(
                report.isDeleted.isFalse(),
                report.schoolYear.eq(schoolYear),
                report.schoolId.eq(schoolId),
                report.userId.eq(userId)
            )
            .orderBy(report.lastModifiedDate.desc())
            .fetch();
    }
}
