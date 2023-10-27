package com.everyschool.callservice.api.client;

import com.everyschool.callservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping
    UserInfo searchUserInfo(@RequestHeader("Authorization") String token);

    @GetMapping
    UserInfo searchUserInfoByUserKey(@RequestParam String otherUserKey);

}
