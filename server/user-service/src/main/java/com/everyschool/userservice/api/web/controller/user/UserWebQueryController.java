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

/**
 * 회원 웹 조회 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/web")
public class UserWebQueryController {

    private final UserWebQueryService userWebQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 교직원 회원 정보 조회 API
     *
     * @return 조회된 교직원 회원 정보
     */
    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> searchUserInfo() {

        String userKey = tokenUtils.getUserKey();

        UserInfoResponse response = userWebQueryService.searchUserInfo(userKey);

        return ApiResponse.ok(response);
    }
}
