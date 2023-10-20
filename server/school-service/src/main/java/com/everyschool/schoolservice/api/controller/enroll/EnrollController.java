package com.everyschool.schoolservice.api.controller.enroll;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.enroll.request.EnrollRequest;
import com.everyschool.schoolservice.api.controller.enroll.response.EnrollResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class EnrollController {

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
     * 등록 조회 API
     *
     * @return 등록 조회
     */
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


}
