package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 신고 저장소
 *
 * @author 임우택
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
