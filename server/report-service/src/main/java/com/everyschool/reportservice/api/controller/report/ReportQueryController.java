package com.everyschool.reportservice.api.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.controller.report.response.MyReportResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.service.report.ReportQueryService;
import com.everyschool.reportservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/schools/{schoolId}")
public class ReportQueryController {

    private final ReportQueryService reportQueryService;
    private final TokenUtils tokenUtils;

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

    @GetMapping("/reports")
    public ApiResponse<List<MyReportResponse>> searchReports(@PathVariable String schoolId) {
        log.debug("call ReportQueryController#searchReports");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyReportResponse> responses = reportQueryService.searchReports(userKey);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/reports/{reportId}")
    public ApiResponse<ReportDetailResponse> searchReport(@PathVariable Long schoolId, @PathVariable Long reportId) {
        log.debug("call ReportQueryController#searchReport");
        log.debug("reportId={}", reportId);

        ReportDetailResponse response = reportQueryService.searchReport(reportId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
