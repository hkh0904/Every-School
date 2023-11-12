package com.everyschool.schoolservice.api.app.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolDetailResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.api.service.school.SchoolAppQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 웹 학교 조회용 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools")
public class SchoolAppQueryController {

    private final SchoolAppQueryService schoolAppQueryService;

    /**
     * 학교 목록 조회 API
     *
     * @param query 검색 쿼리
     * @return 조회된 학교 목록
     */
    @GetMapping
    public ApiResponse<List<SchoolResponse>> searchSchools(@RequestParam String query) {
        log.debug("call SchoolAppQueryController#searchSchools");
        log.debug("query={}", query);

        List<SchoolResponse> responses = schoolAppQueryService.searchSchools(query);
        log.debug("search results={}", responses);

        return ApiResponse.ok(responses);
    }

    /**
     * 학교 상세 정보 조회 API
     *
     * @param schoolId 학교 아이디
     * @return 학교 상세 정보
     */
    @GetMapping("/{schoolId}")
    public ApiResponse<SchoolDetailResponse> searchSchool(@PathVariable Long schoolId) {
        log.debug("call SchoolAppQueryController#searchSchool");
        log.debug("schoolId={}", schoolId);

        SchoolDetailResponse response = schoolAppQueryService.searchSchoolInfo(schoolId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
