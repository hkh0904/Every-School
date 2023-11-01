package com.everyschool.reportservice.api.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.controller.FileStore;
import com.everyschool.reportservice.api.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.service.report.ReportService;
import com.everyschool.reportservice.domain.report.UploadFile;
import com.everyschool.reportservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/schools/{schoolId}/reports")
public class ReportController {

    private final ReportService reportService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    /**
     * 신고 등록 API
     *
     * @return 등록 처리
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateReportResponse> createReport(@ModelAttribute CreateReportRequest request, @PathVariable Long schoolId) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateReportResponse response = reportService.createReport(userKey, schoolId, request.toDto(), uploadFiles);

        return ApiResponse.created(response);
    }

}