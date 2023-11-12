package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.api.app.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportAppQueryRepository;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everyschool.reportservice.error.ErrorMessage.NO_SUCH_REPORT;

/**
 * 앱 신고 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportAppQueryService {

    private final ReportRepository reportRepository;
    private final ReportAppQueryRepository reportAppQueryRepository;
    private final UserServiceClient userServiceClient;
    private final FileStore fileStore;

    /**
     * 신고 내역 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @return 조회된 신고 내역 목록
     */
    public List<ReportResponse> searchReports(String userKey, int schoolYear, Long schoolId) {
        //회원 정보 조회
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        //신고 내역 목록 조회
        List<ReportResponse> content = reportAppQueryRepository.findByUserId(userInfo.getUserId(), schoolYear, schoolId);

        return content;
    }

    /**
     * 신고 내역 상세 조회
     *
     * @param reportId 신고 아이디
     * @return 조회된 신고 내역
     */
    public ReportDetailResponse searchReport(Long reportId) {
        //신고 엔티티 조회
        Report report = getReportEntity(reportId);

        //저장된 파일 url 조회
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
