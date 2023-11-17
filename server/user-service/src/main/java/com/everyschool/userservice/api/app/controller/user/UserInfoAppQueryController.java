package com.everyschool.userservice.api.app.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.StudentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherInfoResponse;
import com.everyschool.userservice.api.app.service.user.UserAppQueryService;
import com.everyschool.userservice.api.app.service.user.dto.ParentInfoResponseDto;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 앱 정보 조회 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/app")
public class UserInfoAppQueryController {

    private final UserAppQueryService userAppQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 학생 회원 정보 조회 API
     *
     * @return 조회된 회원 정보
     */
    @GetMapping("/info/student")
    public ApiResponse<StudentInfoResponse> searchStudentInfo() {

        String userKey = tokenUtils.getUserKey();

        StudentInfoResponse response = userAppQueryService.searchStudentInfo(userKey);

        if (response.getSchool().getName().equals("미승인")) {
            return ApiResponse.of(HttpStatus.OK, "학급 승인 대기중입니다.", response);
        }

        if (response.getSchool().getName().equals("미신청")) {
            return ApiResponse.of(HttpStatus.OK, "학급 신청 후 이용바랍니다.", response);
        }

        return ApiResponse.ok(response);
    }

    /**
     * 학부모 회원 정보 조회 API
     *
     * @return 조회된 회원 정보
     */
    @GetMapping("/info/parent")
    public ApiResponse<ParentInfoResponse> searchParentInfo() {

        String userKey = tokenUtils.getUserKey();

        ParentInfoResponseDto dto = userAppQueryService.searchParentInfo(userKey);
        ParentInfoResponse response = dto.getParentInfoResponse();

        if (dto.getStatus() == 0) {
            return ApiResponse.of(HttpStatus.OK, "자녀 등록 승인 대기 중 입니다.", response);
        } else if (dto.getStatus() == -1) {
            return ApiResponse.of(HttpStatus.OK, "자녀 등록이 필요합니다.", response);
        }

        return ApiResponse.ok(response);
    }

    /**
     * 교직원 회원 정보 조회 API
     *
     * @return 조회된 회원 정보
     */
    @GetMapping("/info/teacher")
    public ApiResponse<TeacherInfoResponse> searchTeacherInfo() {

        String userKey = tokenUtils.getUserKey();

        TeacherInfoResponse response = userAppQueryService.searchTeacherInfo(userKey);

        return ApiResponse.ok(response);
    }
}
