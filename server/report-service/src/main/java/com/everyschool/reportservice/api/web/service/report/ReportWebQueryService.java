package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.repository.ReportWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportWebQueryService {

    private final ReportWebQueryRepository reportWebQueryRepository;

    public List<ReportResponse> searchReports(Long schoolId, int schoolYear, int status) {
        List<Report> reports = reportWebQueryRepository.findByCond(schoolId, schoolYear, status);

        return reports.stream()
            .map(ReportResponse::of)
            .collect(Collectors.toList());
    }
}
