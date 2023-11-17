package com.everyschool.schoolservice.api.app.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.app.controller.schoolapply.request.CreateSchoolApplyRequest;
import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 앱 학교 소속 신청 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/app/{schoolYear}/schools/{schoolId}/apply")
public class SchoolApplyAppController {

    private final SchoolApplyAppService schoolApplyAppService;
    private final TokenUtils tokenUtils;

    /**
     * 학교 소속 신청 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param request    신청 정보
     * @return 신청된 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateSchoolApplyResponse> createSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @RequestBody CreateSchoolApplyRequest request
    ) {
        String userKey = tokenUtils.getUserKey();

        CreateSchoolApplyResponse response = schoolApplyAppService.createSchoolApply(userKey, schoolYear, schoolId, request.toDto());

        return ApiResponse.created(response);
    }
}
