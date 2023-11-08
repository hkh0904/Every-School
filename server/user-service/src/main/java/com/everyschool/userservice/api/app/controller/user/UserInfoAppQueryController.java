package com.everyschool.userservice.api.app.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.StudentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherInfoResponse;
import com.everyschool.userservice.api.app.service.user.UserAppQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/app")
public class UserInfoAppQueryController {

    private final UserAppQueryService userAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/info/student")
    public ApiResponse<StudentInfoResponse> searchStudentInfo() {

        String userKey = tokenUtils.getUserKey();

        StudentInfoResponse response = userAppQueryService.searchStudentInfo(userKey);

        return ApiResponse.ok(response);
    }

    @GetMapping("/info/parent")
    public ApiResponse<ParentInfoResponse> searchParentInfo() {

        String userKey = tokenUtils.getUserKey();

        ParentInfoResponse response = userAppQueryService.searchParentInfo(userKey);

        return ApiResponse.ok(response);
    }

    @GetMapping("/info/teacher")
    public ApiResponse<TeacherInfoResponse> searchTeacherInfo() {

        String userKey = tokenUtils.getUserKey();

        TeacherInfoResponse response = userAppQueryService.searchTeacherInfo(userKey);

        return ApiResponse.ok(response);
    }
}
