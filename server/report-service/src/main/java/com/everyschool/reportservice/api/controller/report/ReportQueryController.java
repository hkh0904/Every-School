package com.everyschool.reportservice.api.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.controller.report.response.ReceivedReportResponse;
import com.everyschool.reportservice.api.service.report.ReportQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/schools/{schoolId}")
public class ReportQueryController {

    private final ReportQueryService reportQueryService;

    @GetMapping("/received-reports")
    public ApiResponse<ReceivedReportResponse> searchReceivedReport(@PathVariable Long schoolId) {
        log.debug("call ReportQueryController#searchReceivedReport");

        ReceivedReportResponse response = reportQueryService.searchReceivedReport(schoolId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
