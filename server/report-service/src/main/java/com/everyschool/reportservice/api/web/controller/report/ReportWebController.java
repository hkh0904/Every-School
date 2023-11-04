package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports")
public class ReportWebController {

    private final ReportWebService reportWebService;

    @PatchMapping("/{reportId}")
    public ApiResponse<EditReportResponse> editStatus(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId,
        @RequestParam Integer status
    ) {
        EditReportResponse response = reportWebService.editStatus(reportId, status);

        return ApiResponse.ok(response);
    }
}
