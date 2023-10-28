package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class UserQueryController {

    private final UserQueryService userQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> searchUserInfo() {
        log.debug("call UserQueryController#searchUserInfo");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        UserInfoResponse response = userQueryService.searchUser(userKey);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }

}
