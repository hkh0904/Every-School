package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.api.service.school.SchoolQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools")
public class SchoolQueryController {

    private final SchoolQueryService schoolQueryService;

    /**
     * 학교 목록 조회 API
     *
     * @return 학교 리스트
     */
    @GetMapping
    public ApiResponse<List<SchoolResponse>> searchSchools(@RequestParam String query) {
        log.debug("call SchoolQueryController#searchSchools");
        log.debug("query={}", query);

        List<SchoolResponse> responses = schoolQueryService.searchSchools(query);
         log.debug("search results={}", responses);

        return ApiResponse.ok(responses);
    }

    /**
     * 학교 단건 조회 API
     *
     * @return 학교 정보
     */
    @GetMapping("/{schoolId}")
    public ApiResponse<SchoolResponse> searchSchool(@PathVariable Long schoolId) {
        return ApiResponse.ok(schoolQueryService.searchOneSchool(schoolId));
    }
}
