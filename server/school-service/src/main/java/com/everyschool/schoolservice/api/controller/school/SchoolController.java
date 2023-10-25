package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.school.request.EnrollRequest;
import com.everyschool.schoolservice.api.controller.school.response.EnrollResponse;
import com.everyschool.schoolservice.api.controller.school.request.ClassroomRequest;
import com.everyschool.schoolservice.api.controller.school.response.ClassroomResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.api.controller.school.response.UserEnrollResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1")
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

    /**
     * 학교 등록 요청 API
     *
     * @return ok
     */
    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createEnroll(@RequestBody EnrollRequest request) {
        String res = "신청 완료";
        return ApiResponse.ok(res);
    }

    /**
     * (교사용) 등록 요청 리스트 조회 API
     *
     * @return 요청 정보 리스트
     */
    @GetMapping("/enroll/teacher/{schoolClassId}")
    public ApiResponse<List<UserEnrollResponse>> searchEnrolls(@PathVariable Long schoolClassId) {
         UserEnrollResponse response1 = UserEnrollResponse.builder()
                .type('P')
                .grade(1)
                .classNum(6)
                .studentNum(22)
                .childName("이지혁")
                .appliedDate(LocalDateTime.now())
                .build();

        UserEnrollResponse response2 = UserEnrollResponse.builder()
                .type('C')
                .grade(1)
                .classNum(6)
                .studentNum(22)
                .childName("이지혁")
                .appliedDate(LocalDateTime.now())
                .build();

        List<UserEnrollResponse> resList = new ArrayList<>();
        resList.add(response1);
        resList.add(response2);

        return ApiResponse.ok(resList);
    }

    @GetMapping("/enroll/{userKey}")
    public ApiResponse<EnrollResponse> searchMyEnroll(@PathVariable String userKey) {
        EnrollResponse response = EnrollResponse.builder()
                .year(2023)
                .schoolName("싸피초등학교")
                .grade(1)
                .classNum(6)
                .name("이지혁")
                .isApproved(false)
                .rejectedReason("승인 대기중")
                .build();
        return ApiResponse.ok(response);
    }

    /**
     * 내 학급 조회 API
     *
     * @return 내 학급 정보
     */
    @GetMapping("/classroom/{userKey}")
    public ApiResponse<ClassroomResponse> searchMyClassroom(@PathVariable String userKey) {
        ClassroomResponse response = ClassroomResponse.builder()
                .teacherName("임우택")
                .year(2023)
                .grade(1)
                .name("동팔이")
                .build();

        return ApiResponse.ok(response);
    }
}
