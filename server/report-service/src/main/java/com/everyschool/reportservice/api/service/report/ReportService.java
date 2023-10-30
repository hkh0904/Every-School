package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.api.service.report.dto.CreateReportDto;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    public void createReport(String userKey, CreateReportDto dto) {

    }
}
