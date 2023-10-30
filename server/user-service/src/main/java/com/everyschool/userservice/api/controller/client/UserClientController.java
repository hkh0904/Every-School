package com.everyschool.userservice.api.controller.client;

import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.controller.user.request.UserIdRequest;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/client/v1")
public class UserClientController {

    private final UserQueryService userQueryService;
    private final TokenUtils tokenUtils;

    @PostMapping("/user-id")
    public UserClientResponse searchUserId(@RequestBody UserIdRequest request) {
        log.debug("call UserClientController#searchUserId");
        log.debug("userKey={}", request.getUserKey());

        UserClientResponse response = userQueryService.searchUserId(request.getUserKey());
        log.debug("response={}", response);

        return response;
    }

    @GetMapping("/user-info")
    public UserInfo searchUserInfo() {
        log.debug("call UserClientController#searchUserInfo");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        UserInfo userInfo = userQueryService.searchUserInfo(userKey);
        log.debug("UserInfo={}", userInfo);

        return userInfo;
    }
}
