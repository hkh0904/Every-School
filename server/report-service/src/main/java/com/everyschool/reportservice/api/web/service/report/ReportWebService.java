package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.reportservice.error.ErrorMessage.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportWebService {

    private final ReportRepository reportRepository;

    public EditReportResponse editStatus(Long reportId, int statusId) {
        Optional<Report> findReport = reportRepository.findById(reportId);
        if (findReport.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_REPORT.getMessage());
        }
        Report report = findReport.get();

        Report editReport = report.editStatus(statusId);

        return EditReportResponse.of(editReport);
    }
}
