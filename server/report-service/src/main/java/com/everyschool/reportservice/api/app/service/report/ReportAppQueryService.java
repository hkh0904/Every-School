package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.api.app.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.domain.report.AttachedFile;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportAppQueryRepository;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_REPORT;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportAppQueryService {

    private final ReportRepository reportRepository;
    private final ReportAppQueryRepository reportAppQueryRepository;
    private final UserServiceClient userServiceClient;
    private final FileStore fileStore;

    public List<ReportResponse> searchReports(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        List<ReportResponse> response = reportAppQueryRepository.findByUserId(userInfo.getUserId(), schoolYear);

        return response;
    }

    public ReportDetailResponse searchReport(Long reportId) {
        Report report = getReportEntity(reportId);

        List<String> files = report.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        return ReportDetailResponse.of(report, files);
    }

    private Report getReportEntity(Long reportId) {
        Optional<Report> findReport = reportRepository.findById(reportId);
        if (findReport.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_REPORT.getMessage());
        }
        return findReport.get();
    }
}
