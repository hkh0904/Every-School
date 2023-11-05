package com.everyschool.userservice.api.web.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.web.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.web.service.user.UserWebQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/web")
public class UserWebQueryController {

    private final UserWebQueryService userWebQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> searchUserInfo() {

        String userKey = tokenUtils.getUserKey();

        UserInfoResponse response = userWebQueryService.searchUserInfo(userKey);

        return ApiResponse.ok(response);
    }
}
