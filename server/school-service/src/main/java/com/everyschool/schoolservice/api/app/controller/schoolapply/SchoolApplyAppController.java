package com.everyschool.schoolservice.api.app.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        // TODO: 11/6/23 작업중
//        schoolApplyAppService.createSchoolApply(userKey, schoolYear, )

        return ApiResponse.created(null);
    }
}
