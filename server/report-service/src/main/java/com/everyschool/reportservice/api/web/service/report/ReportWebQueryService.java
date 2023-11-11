package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.api.web.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import com.everyschool.reportservice.domain.report.repository.ReportWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everyschool.reportservice.error.ErrorMessage.NO_SUCH_REPORT;

/**
 * 웹 신고 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportWebQueryService {

    private final ReportRepository reportRepository;
    private final ReportWebQueryRepository reportWebQueryRepository;
    private final FileStore fileStore;

    /**
     * 신고 내역 목록 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param status     조회할 신고 처리 상태
     * @return 조회된 신고 내역 목록
     */
    public List<ReportResponse> searchReports(int schoolYear, Long schoolId, int status) {

        List<Report> reports = reportWebQueryRepository.findByStatus(schoolId, schoolYear, status);

        return reports.stream()
            .map(ReportResponse::of)
            .collect(Collectors.toList());
    }

    /**
     * 신고 내역 상세 조회
     *
     * @param reportId 신고 아이디
     * @return 조회된 신고 내역
     */
    public ReportDetailResponse searchReport(Long reportId) {
        Report report = getReportEntity(reportId);

        List<String> files = report.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        return ReportDetailResponse.of(report, files);
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
