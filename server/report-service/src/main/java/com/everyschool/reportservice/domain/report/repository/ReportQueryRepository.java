package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.api.controller.report.response.MyReportResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.reportservice.domain.report.QReport.report;

@Repository
public class ReportQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ReportResponse.ReportVo> searchReceivedReports(Long schoolId, ProgressStatus status) {
        return queryFactory
            .select(Projections.constructor(
                ReportResponse.ReportVo.class,
                report.id,
                report.typeId,
                report.lastModifiedDate
            ))
            .from(report)
            .where(
                report.schoolId.eq(schoolId),
                report.progressStatusId.eq(status.getCode())
            )
            .orderBy(report.lastModifiedDate.desc())
            .fetch();
    }

    public List<ReportResponse.ReportVo> searchProcessedReports(Long schoolId, ProgressStatus status) {
        return queryFactory
            .select(Projections.constructor(
                ReportResponse.ReportVo.class,
                report.id,
                report.typeId,
                report.lastModifiedDate
            ))
            .from(report)
            .where(
                report.schoolId.eq(schoolId),
                report.progressStatusId.ne(status.getCode())
            )
            .orderBy(report.lastModifiedDate.desc())
            .fetch();
    }

    public int searchCountReceviedReports(Long schoolId, ProgressStatus status) {
        return queryFactory
            .select(report.id)
            .from(report)
            .where(
                report.schoolId.eq(schoolId),
                report.progressStatusId.eq(status.getCode())
            )
            .fetch()
            .size();
    }

    public int searchCountProcessedReports(Long schoolId, ProgressStatus status) {
        return queryFactory
            .select(report.id)
            .from(report)
            .where(
                report.schoolId.eq(schoolId),
                report.progressStatusId.ne(status.getCode())
            )
            .fetch()
            .size();
    }

    public List<MyReportResponse> findByUserId(Long userId) {
        return queryFactory
            .select(Projections.constructor(
                MyReportResponse.class,
                report.id,
                report.typeId,
                report.progressStatusId,
                report.createdDate
            ))
            .from(report)
            .where(report.userId.eq(userId))
            .orderBy(report.createdDate.desc())
            .fetch();
    }
}
