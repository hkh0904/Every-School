package com.everyschool.userservice.api.controller.client;

import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/client/v1")
public class UserClientController {

    private final UserQueryService userQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/user-info")
    public UserInfo searchUserInfo() {
        log.debug("call UserClientController#searchUserInfo");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        UserInfo userInfo = userQueryService.searchUserInfo(userKey);
        log.debug("UserInfo={}", userInfo);

        return userInfo;
    }

    @GetMapping("/user-info/{userKey}")
    public UserInfo searchUserInfo(@PathVariable String userKey) {
        log.debug("call UserClientController#searchUserInfo");

        UserInfo userInfo = userQueryService.searchUserInfo(userKey);
        log.debug("UserInfo={}", userInfo);

        return userInfo;
    }
}
