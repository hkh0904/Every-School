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

import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_REPORT;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportWebQueryService {

    private final ReportRepository reportRepository;
    private final ReportWebQueryRepository reportWebQueryRepository;
    private final FileStore fileStore;

    public List<ReportResponse> searchReports(Long schoolId, int schoolYear, int status) {
        List<Report> reports = reportWebQueryRepository.findByCond(schoolId, schoolYear, status);

        return reports.stream()
            .map(ReportResponse::of)
            .collect(Collectors.toList());
    }

    public ReportDetailResponse searchReport(Long reportId) {
        Optional<Report> findReport = reportRepository.findById(reportId);
        if (findReport.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_REPORT.getMessage());
        }
        Report report = findReport.get();

        List<String> files = report.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        return ReportDetailResponse.of(report, files);
    }
}
