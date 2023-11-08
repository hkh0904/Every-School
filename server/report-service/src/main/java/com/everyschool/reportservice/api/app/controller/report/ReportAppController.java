package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.app.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.app.service.report.ReportAppService;
import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.domain.report.UploadFile;
import com.everyschool.reportservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports")
public class ReportAppController {

    private final ReportAppService reportAppService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateReportResponse> createReport(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @ModelAttribute CreateReportRequest request
    ) throws IOException {
        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateReportResponse response = reportAppService.createReport(userKey, schoolId, schoolYear, request.toDto(), uploadFiles);

        return ApiResponse.created(response);
    }

}
