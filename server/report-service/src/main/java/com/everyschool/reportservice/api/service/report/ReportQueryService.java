package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.repository.ReportQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportQueryService {

    private final ReportQueryRepository reportQueryRepository;

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
}
