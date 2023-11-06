package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.app.service.report.ReportAppQueryService;
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
@RequestMapping("/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports")
public class ReportAppQueryController {

    private final ReportAppQueryService reportAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<List<ReportResponse>> searchReports(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<ReportResponse> response =  reportAppQueryService.searchReports(userKey, schoolYear);

        return ApiResponse.ok(response);
    }
}
