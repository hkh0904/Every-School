package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.web.controller.report.request.EditResultRequest;
import com.everyschool.reportservice.api.web.controller.report.request.EditStatusRequest;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebService;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 웹 신고 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports")
public class ReportWebController {

    private final ReportWebService reportWebService;

    /**
     * 신고 처리 상태 수정 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param reportId   신고 아이디
     * @param request    변경할 상태 정보
     * @return 변경된 신고 내역 정보
     */
    @PatchMapping("/{reportId}")
    public ApiResponse<EditReportResponse> editStatus(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId,
        @Valid @RequestBody EditStatusRequest request
    ) {

        validateProgressStatus(request.getStatus());

        EditReportResponse response = reportWebService.editStatus(reportId, request.getStatus());

        return ApiResponse.ok(response);
    }

    /**
     * 신고 처리 결과 수정 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param reportId   신고 아이디
     * @param request    변경할 신고 처리 결과 내용
     * @return 변경된 신고 내역 정보
     */
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

    /**
     * 신고 처리 상태 코드 검증
     *
     * @param code 신고 타입 코드
     */
    private void validateProgressStatus(int code) {
        ProgressStatus.getText(code);
    }
}
