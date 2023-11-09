package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.web.controller.report.request.EditResultRequest;
import com.everyschool.reportservice.api.web.controller.report.request.EditStatusRequest;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebService;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        @Valid @RequestBody EditStatusRequest request
    ) {
        ProgressStatus.getText(request.getStatus());

        EditReportResponse response = reportWebService.editStatus(reportId, request.getStatus());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{reportId}/result")
    public ApiResponse<EditReportResponse> editResult(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId,
        @Valid @RequestBody EditResultRequest request
    ) {
        EditReportResponse response = reportWebService.editResult(reportId, request.getResult());

        return ApiResponse.ok(response);
    }
}
