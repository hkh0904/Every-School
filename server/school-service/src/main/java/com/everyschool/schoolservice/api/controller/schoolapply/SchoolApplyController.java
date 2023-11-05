package com.everyschool.schoolservice.api.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.schoolapply.request.CreateSchoolApplyRequest;
import com.everyschool.schoolservice.api.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools/{schoolId}/apply")
public class SchoolApplyController {

    private final SchoolApplyService schoolApplyService;
    private final TokenUtils tokenUtils;

    // TODO: 11/6/23 이전중
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateSchoolApplyResponse> createSchoolApply(@PathVariable Long schoolId, @RequestBody CreateSchoolApplyRequest request) {
        log.debug("call SchoolApplyController#createSchoolApply");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        CreateSchoolApplyResponse response = schoolApplyService.createSchoolApply(schoolId, userKey, request.toDto());
        log.debug("result={}", response);

        return ApiResponse.created(response);
    }
}
