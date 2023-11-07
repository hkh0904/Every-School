package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.QReport;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.reportservice.domain.report.QReport.report;

@Repository
public class ReportAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ReportResponse> findByUserId(Long userId, Integer schoolYear) {
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
                report.userId.eq(userId)
            )
            .orderBy(report.createdDate.desc())
            .fetch();
    }
}
