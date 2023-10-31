package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.api.controller.report.response.ReceivedReportResponse;
import com.everyschool.reportservice.api.controller.report.response.vo.ReceivedReportVo;
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

    public ReceivedReportResponse searchReceivedReport(Long schoolId) {
        List<ReceivedReportVo> data = reportQueryRepository.searchReports(schoolId, ProgressStatus.REGISTER);
        IntStream
            .range(0, data.size())
            .forEach(i -> data.get(i).setNo(i + 1));
        int count = reportQueryRepository.searchCountReports(schoolId, ProgressStatus.REGISTER);

        return ReceivedReportResponse.builder()
            .count(count)
            .reports(data)
            .build();
    }
}
