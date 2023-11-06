package com.everyschool.schoolservice.api.web.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.Result;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.api.web.service.schoolapply.SchoolApplyWebQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
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
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}")
public class SchoolApplyWebQueryController {

    private final SchoolApplyWebQueryService schoolApplyWebQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/wait-apply")
    public ApiResponse<Result<SchoolApplyResponse>> searchWaitSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<SchoolApplyResponse> response = schoolApplyWebQueryService.searchWaitSchoolApply(userKey, schoolYear);

        return ApiResponse.ok(Result.of(response));
    }

    @GetMapping("/approve-apply")
    public ApiResponse<Result<SchoolApplyResponse>> searchApproveSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<SchoolApplyResponse> response = schoolApplyWebQueryService.searchApproveSchoolApply(userKey, schoolYear);

        return ApiResponse.ok(Result.of(response));
    }

    @GetMapping("/apply/{schoolApplyId}")
    public ApiResponse<SchoolApplyResponse> searchSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolApplyId
    ) {

        SchoolApplyResponse response = schoolApplyWebQueryService.searchSchoolApply(schoolApplyId);

        return ApiResponse.ok(response);
    }
}
