package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.api.client.SchoolServiceClient;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.StudentInfo;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.api.controller.FileStore;
import com.everyschool.reportservice.api.controller.report.response.MyReportResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportQueryRepository;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportQueryService {

    private final ReportRepository reportRepository;
    private final ReportQueryRepository reportQueryRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;
    private final FileStore fileStore;

    public ReportResponse searchReceivedReports(Long schoolId) {
        List<ReportResponse.ReportVo> reports = reportQueryRepository.searchReceivedReports(schoolId, ProgressStatus.REGISTER);
        IntStream
            .range(0, reports.size())
            .forEach(i -> reports.get(i).setNo(i + 1));
        int count = reportQueryRepository.searchCountReceviedReports(schoolId, ProgressStatus.REGISTER);

        return ReportResponse.builder()
            .count(count)
            .reports(reports)
            .build();
    }

    public ReportResponse searchProcessedReports(Long schoolId) {
        List<ReportResponse.ReportVo> reports = reportQueryRepository.searchProcessedReports(schoolId, ProgressStatus.REGISTER);
        IntStream
            .range(0, reports.size())
            .forEach(i -> reports.get(i).setNo(i + 1));
        int count = reportQueryRepository.searchCountProcessedReports(schoolId, ProgressStatus.REGISTER);

        return ReportResponse.builder()
            .count(count)
            .reports(reports)
            .build();
    }

    public List<MyReportResponse> searchReports(String userKey) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        List<MyReportResponse> responses = reportQueryRepository.findByUserId(userInfo.getUserId());

        return responses;
    }

    public ReportDetailResponse searchReport(Long reportId) {
        Optional<Report> findReport = reportRepository.findById(reportId);
        if (findReport.isEmpty()) {
            throw new NoSuchElementException();
        }
        Report report = findReport.get();

        UserInfo userInfo = userServiceClient.searchByUserId(report.getUserId());

        StudentInfo studentInfo = schoolServiceClient.searchByUserId(report.getUserId());

        List<String> filePaths = report.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        return ReportDetailResponse.of(report, userInfo, studentInfo, filePaths);
    }
}
