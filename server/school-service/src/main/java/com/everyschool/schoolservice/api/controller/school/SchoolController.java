package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.request.ClassroomRequest;
import com.everyschool.schoolservice.api.controller.school.response.ClassroomResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 학급 생성 API
     *
     * @return 생성한 학급 정보
     */
    @PostMapping("/school/{schoolId}/classroom")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ClassroomResponse> createClassroom(@PathVariable String schoolId,
                                                       @RequestBody ClassroomRequest request) {

        ClassroomResponse res = ClassroomResponse.builder()
                .teacherName("임우택")
                .year(2023)
                .grade(1)
                .name("동팔이")
                .build();

        return ApiResponse.ok(res);
    }

}
