package com.everyschool.userservice.api.controller.client;

import com.everyschool.userservice.api.controller.user.request.UserIdRequest;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/client/v1")
public class UserClientController {

    private final UserQueryService userQueryService;

    @PostMapping("/user-id")
    public UserClientResponse searchUserId(@RequestBody UserIdRequest request) {
        log.debug("call UserClientController#searchUserId");
        log.debug("userKey={}", request.getUserKey());

        UserClientResponse response = userQueryService.searchUserId(request.getUserKey());
        log.debug("response={}", response);

        return response;
    }
}
