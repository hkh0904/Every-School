package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.domain.report.Report;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.reportservice.domain.report.QReport.*;

/**
 * 웹 신고 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class ReportWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 신고 처리 상태에 따른 신고 내역 목록 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param status     신고 처리 상태 코드
     * @return 조회된 신고 내역 목록
     */
    public List<Report> findByStatus(int schoolYear, Long schoolId, int status) {
        return queryFactory
            .select(report)
            .from(report)
            .where(
                report.isDeleted.isFalse(),
                report.schoolYear.eq(schoolYear),
                report.schoolId.eq(schoolId),
                report.progressStatusId.eq(status)
            )
            .orderBy(report.lastModifiedDate.desc())
            .fetch();
    }
}
