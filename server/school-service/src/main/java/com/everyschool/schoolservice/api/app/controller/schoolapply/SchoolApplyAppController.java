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

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/app/{schoolYear}/schools/{schoolId}/apply")
public class SchoolApplyAppController {

    private final SchoolApplyAppService schoolApplyAppService;
    private final TokenUtils tokenUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateSchoolApplyResponse> createSchoolApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @RequestBody CreateSchoolApplyRequest request
    ) {
        String userKey = tokenUtils.getUserKey();

        CreateSchoolApplyResponse response = schoolApplyAppService.createSchoolApply(userKey, schoolId, schoolYear, request.toDto());

        return ApiResponse.created(response);
    }
}
