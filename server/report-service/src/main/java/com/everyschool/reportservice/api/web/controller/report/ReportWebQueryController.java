package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.Result;
import com.everyschool.reportservice.api.web.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebQueryService;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 웹 신고 조회용 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports")
public class ReportWebQueryController {

    private final ReportWebQueryService reportWebQueryService;

    /**
     * 신고 내역 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param status     조회할 신고 처리 상태 코드
     * @return 조회된 신고 내역 목록
     */
    @GetMapping
    public ApiResponse<Result<ReportResponse>> searchReports(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam Integer status
    ) {

        validateReportType(status);

        List<ReportResponse> response = reportWebQueryService.searchReports(schoolYear, schoolId, status);

        return ApiResponse.ok(Result.of(response));
    }

    /**
     * 신고 내역 상세 조회 API
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

        ReportDetailResponse response = reportWebQueryService.searchReport(reportId);

        return ApiResponse.ok(response);
    }

    /**
     * 신고 타입 코드 검증
     *
     * @param code 신고 타입 코드
     */
    private void validateReportType(int code) {
        ReportType.getText(code);
    }
}
