package com.everyschool.schoolservice.api.web.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.web.controller.schoolapply.request.RejectSchoolApplyRequest;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.EditSchoolApplyResponse;
import com.everyschool.schoolservice.api.web.service.schoolapply.SchoolApplyWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 웹 학교 소속 신청 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}/apply")
public class SchoolApplyWebController {

    private final SchoolApplyWebService schoolApplyWebService;

    /**
     * 학교 소속 신청 승인 API
     *
     * @param schoolYear    학년도
     * @param schoolId      학교 아이디
     * @param schoolApplyId 신청 아이디
     * @return 승인된 신청 정보
     */
    @PatchMapping("/{schoolApplyId}/approve")
    public ApiResponse<EditSchoolApplyResponse> approveSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolApplyId
    ) {
        EditSchoolApplyResponse response = schoolApplyWebService.approveSchoolApply(schoolApplyId);

        return ApiResponse.ok(response);
    }

    /**
     * 학교 소속 신청 거절 API
     *
     * @param schoolYear    학년도
     * @param schoolId      학교 아이디
     * @param schoolApplyId 신청 아이디
     * @param request       신청 거절 사유
     * @return 거절된 신청 정보
     */
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
