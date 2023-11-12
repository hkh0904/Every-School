package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.app.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.app.controller.report.response.RemoveReportResponse;
import com.everyschool.reportservice.api.app.service.report.ReportAppService;
import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.domain.report.ReportType;
import com.everyschool.reportservice.domain.report.UploadFile;
import com.everyschool.reportservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 신고 App API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports")
public class ReportAppController {

    private final ReportAppService reportAppService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    /**
     * 신고 등록 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param request    신고 등록 요청 정보
     * @return 등록된 신고 정보
     * @throws IOException S3에 파일 업로드 실패 시 발생
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateReportResponse> createReport(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @ModelAttribute CreateReportRequest request
    ) throws IOException {

        validateReportType(request.getTypeId());

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateReportResponse response = reportAppService.createReport(userKey, schoolYear, schoolId, request.toDto(), uploadFiles);

        return ApiResponse.created(response);
    }

    /**
     * 신고 내역 삭제 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param reportId   신고 아이디
     * @return 삭제된 신고 내역 정보
     */
    @DeleteMapping("/{reportId}")
    public ApiResponse<?> removeReport(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long reportId
    ) {

        RemoveReportResponse response = reportAppService.removeReport(reportId);

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
