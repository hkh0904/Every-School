package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.Result;
import com.everyschool.reportservice.api.web.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports")
public class ReportWebQueryController {

    private final ReportWebQueryService reportWebQueryService;

    @GetMapping
    public ApiResponse<Result<ReportResponse>> searchReports(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam Integer status
    ) {
        List<ReportResponse> response = reportWebQueryService.searchReports(schoolId, schoolYear, status);

        return ApiResponse.ok(Result.of(response));
    }

    @GetMapping("/{reportId}")
    public ApiResponse<ReportDetailResponse> searchReport(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId
    ) {

        ReportDetailResponse response = reportWebQueryService.searchReport(reportId);

        return ApiResponse.ok(response);
    }
}
