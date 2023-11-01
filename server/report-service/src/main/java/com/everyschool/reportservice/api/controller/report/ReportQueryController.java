package com.everyschool.reportservice.api.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
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
    public ApiResponse<ReportResponse> searchReceivedReports(@PathVariable Long schoolId) {
        log.debug("call ReportQueryController#searchReceivedReport");

        ReportResponse response = reportQueryService.searchReceivedReports(schoolId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }

    @GetMapping("/processed-reports")
    public ApiResponse<ReportResponse> searchProcessedReports(@PathVariable Long schoolId) {
        log.debug("call ReportQueryController#searchProcessedReport");

        ReportResponse response = reportQueryService.searchProcessedReports(schoolId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
