package com.everyschool.schoolservice.api.web.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.web.controller.schoolapply.request.RejectSchoolApplyRequest;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.EditSchoolApplyResponse;
import com.everyschool.schoolservice.api.web.service.schoolapply.SchoolApplyWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}/apply")
public class SchoolApplyWebController {

    private final SchoolApplyWebService schoolApplyWebService;

    @PatchMapping("/{schoolApplyId}/approve")
    public ApiResponse<EditSchoolApplyResponse> approveSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolApplyId
    ) {
        EditSchoolApplyResponse response = schoolApplyWebService.approveSchoolApply(schoolApplyId);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{schoolApplyId}/reject")
    public ApiResponse<EditSchoolApplyResponse> rejectSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolApplyId,
        @Valid @RequestBody RejectSchoolApplyRequest request
    ) {

        EditSchoolApplyResponse response = schoolApplyWebService.rejectSchoolApply(schoolApplyId, request.getRejectedReason());

        return ApiResponse.ok(response);
    }
}
