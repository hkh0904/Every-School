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

/**
 * 웹 학교 소속 신청 조회용 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}")
public class SchoolApplyWebQueryController {

    private final SchoolApplyWebQueryService schoolApplyWebQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 승인 대기 중인 신청 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 신청 목록
     */
    @GetMapping("/wait-apply")
    public ApiResponse<Result<SchoolApplyResponse>> searchWaitSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<SchoolApplyResponse> response = schoolApplyWebQueryService.searchWaitSchoolApply(userKey, schoolYear);

        return ApiResponse.ok(Result.of(response));
    }

    /**
     * 승인된 신청 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 신청 목록
     */
    @GetMapping("/approve-apply")
    public ApiResponse<Result<SchoolApplyResponse>> searchApproveSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<SchoolApplyResponse> response = schoolApplyWebQueryService.searchApproveSchoolApply(userKey, schoolYear);

        return ApiResponse.ok(Result.of(response));
    }

    /**
     * 신청 내역 상세 조회 API
     *
     * @param schoolYear    학년도
     * @param schoolId      학교 아이디
     * @param schoolApplyId 신청 아이디
     * @return 조회된 신청 내역
     */
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
