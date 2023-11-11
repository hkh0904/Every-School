package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.app.controller.report.response.ReportDetailResponse;
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

/**
 * 신고 App 조회용 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports")
public class ReportAppQueryController {

    private final ReportAppQueryService reportAppQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 나의 신고 내역 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 신고 내역 목록
     */
    @GetMapping
    public ApiResponse<List<ReportResponse>> searchReports(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<ReportResponse> response = reportAppQueryService.searchReports(userKey, schoolYear);

        return ApiResponse.ok(response);
    }

    /**
     * 나의 신고 내역 상세 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param reportId   신고 아이디
     * @return 조회된 신고 내역
     */
    @GetMapping("/{reportId}")
    public ApiResponse<ReportDetailResponse> searchReport(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId
    ) {
        ReportDetailResponse response = reportAppQueryService.searchReport(reportId);

        return ApiResponse.ok(response);
    }
}
