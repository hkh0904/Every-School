package com.everyschool.userservice.api.app.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.app.controller.user.response.StudentContactInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherContactInfoResponse;
import com.everyschool.userservice.api.app.service.user.UserAppQueryService;
import com.everyschool.userservice.utils.TokenUtils;
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
@RequestMapping("/v1/app/{schoolYear}/schools/{schoolId}")
public class UserAppQueryController {

    private final UserAppQueryService userAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/students")
    public ApiResponse<TeacherContactInfoResponse> searchUserContactInfo(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        TeacherContactInfoResponse response = userAppQueryService.searchContactInfo(schoolYear, userKey);

        return ApiResponse.ok(response);
    }

    @GetMapping("/teachers")
    public ApiResponse<List<StudentContactInfoResponse>> searchUserContactInfos(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<StudentContactInfoResponse> response = userAppQueryService.searchContactInfos(schoolYear, userKey);

        return ApiResponse.ok(response);
    }
}
