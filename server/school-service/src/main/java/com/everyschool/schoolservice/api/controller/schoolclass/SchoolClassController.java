package com.everyschool.schoolservice.api.controller.schoolclass;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.schoolclass.request.CreateSchoolClassRequest;
import com.everyschool.schoolservice.api.controller.schoolclass.response.CreateSchoolClassResponse;
import com.everyschool.schoolservice.api.service.schoolclass.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools/{schoolId}/classes")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateSchoolClassResponse> createSchoolClass(
        @PathVariable Long schoolId,
        @RequestBody CreateSchoolClassRequest request
    ) {
        log.debug("call SchoolClassController#createSchoolClass");
        log.debug("schoolId={}", schoolId);
        log.debug("CreateSchoolClassRequest={}", request);

        CreateSchoolClassResponse response = schoolClassService.createSchoolClass(schoolId, request.toDto());
        log.debug("result={}", response);

        return ApiResponse.created(response);
    }
}
