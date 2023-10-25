package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
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

    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> searchUserInfo() {
        // TODO: 2023-10-25 임우택 JWT 복호화 기능 구현
        String email = "ssafy@gmail.com";

        UserInfoResponse response = userQueryService.searchUser(email);

        return ApiResponse.ok(response);
    }

}
