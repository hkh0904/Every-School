package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.api.controller.report.response.vo.ReceivedReportVo;
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

    public List<ReceivedReportVo> searchReports(Long schoolId, ProgressStatus status) {
        return queryFactory
            .select(Projections.constructor(
                ReceivedReportVo.class,
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

    public int searchCountReports(Long schoolId, ProgressStatus status) {
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
}
