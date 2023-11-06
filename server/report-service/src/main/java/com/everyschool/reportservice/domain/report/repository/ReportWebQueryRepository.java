package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.domain.report.Report;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.reportservice.domain.report.QReport.*;

@Repository
public class ReportWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Report> findByCond(Long schoolId, int schoolYear, int status) {
        return queryFactory
            .select(report)
            .from(report)
            .where(
                report.isDeleted.isFalse(),
                report.schoolId.eq(schoolId),
                report.schoolYear.eq(schoolYear),
                report.progressStatusId.eq(status)
            )
            .orderBy(report.lastModifiedDate.desc())
            .fetch();
    }
}
