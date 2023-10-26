package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.api.service.school.SchoolQueryService;
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
@RequestMapping("/school-service/v1")
public class SchoolQueryController {

    private final SchoolQueryService schoolQueryService;

    /**
     * 학교 조회 API
     *
     * @return 학교 리스트
     */
    @GetMapping("/school/{search}")
    public ApiResponse<List<SchoolResponse>> searchSchools(@PathVariable String search) {
        return ApiResponse.ok(schoolQueryService.searchSchools(search));
    }

    /**
     * 학교 단건 조회 API
     *
     * @return 학교 정보
     */
    @GetMapping("/school/{schoolId}")
    public ApiResponse<SchoolResponse> searchSchool(@PathVariable String schoolId) {
        SchoolResponse response = SchoolResponse.builder()
                .name("광주싸피초등학교")
                .address("광주광역시 광산구 하남삼단로")
                .tel("062-1111-2222")
                .url("https://www.ssafy.com/")
                .build();

        return ApiResponse.ok(response);
    }
}
