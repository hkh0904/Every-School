package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class SchoolController {

    /**
     * 학교 조회 API
     *
     * @return 학교 리스트
     */
    @GetMapping("/school")
    public ApiResponse<List<SchoolResponse>> searchSchools() {
        SchoolResponse response1 = SchoolResponse.builder()
                .name("광주싸피초등학교")
                .address("광주광역시 광산구 하남삼단로")
                .tel("062-1111-2222")
                .url("https://www.ssafy.com/")
                .build();

        SchoolResponse response2 = SchoolResponse.builder()
                .name("서울싸피초등학교")
                .address("서울특별시 역삼")
                .tel("02)-1234-1234")
                .url("https://www.ssafy.com/")
                .build();

        List<SchoolResponse> schools = new ArrayList<>();
        schools.add(response1);
        schools.add(response2);

        return ApiResponse.ok(schools);
    }

}
