package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.reportservice.error.ErrorMessage.*;

/**
 * 웹 신고 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ReportWebService {

    private final ReportRepository reportRepository;

    /**
     * 신고 처리 상태 변경
     *
     * @param reportId 신고 아이디
     * @param statusId 신고 처리 상태 코드
     * @return 변경된 신고 내역 정보
     */
    public EditReportResponse editStatus(Long reportId, int statusId) {
        Report report = getReportEntity(reportId);

        Report editReport = report.editStatus(statusId);

        return EditReportResponse.of(editReport);
    }

    /**
     * 신고 처리 결과 변경
     *
     * @param reportId 신고 아이디
     * @param result   신고 처리 결과 정보
     * @return 변경된 신고 내역 정보
     */
    public EditReportResponse editResult(Long reportId, String result) {
        Report report = getReportEntity(reportId);

        Report editReport = report.writeResult(result);

        return EditReportResponse.of(editReport);
    }

    /**
     * 신고 엔티티 조회
     *
     * @param reportId 신고 아이디
     * @return 조회된 신고 엔티티
     * @throws NoSuchElementException 등록되지 않은 신고를 조회하는 경우 발생
     */
    private Report getReportEntity(Long reportId) {
        Optional<Report> findReport = reportRepository.findById(reportId);
        if (findReport.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_REPORT.getMessage());
        }
        return findReport.get();
    }
}
