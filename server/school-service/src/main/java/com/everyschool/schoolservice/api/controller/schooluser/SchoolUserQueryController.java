package com.everyschool.schoolservice.api.controller.schooluser;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.Result;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassParentResponse;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
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
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}/classes/{schoolClassId}")
public class SchoolUserQueryController {

    private final SchoolUserQueryService schoolUserQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/students")
    public ApiResponse<Result<MyClassStudentResponse>> searchMyClassStudents(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolClassId
    ) {
        log.debug("call SchoolUserQueryController#searchMyClassStudents");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyClassStudentResponse> responses = schoolUserQueryService.searchMyClassStudents(userKey, schoolYear);
        log.debug("results={}", responses);

        return ApiResponse.ok(Result.of(responses));
    }

    @GetMapping("/parents")
    public ApiResponse<Result<MyClassParentResponse>> searchMyClassParents(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolClassId
    ) {
        log.debug("call SchoolUserQueryController#searchMyClassParents");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyClassParentResponse> responses = schoolUserQueryService.searchMyClassParents(userKey, schoolYear);
        log.debug("results={}", responses);

        return ApiResponse.ok(Result.of(responses));
    }
}
