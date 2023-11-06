package com.everyschool.callservice.api.client;

import com.everyschool.callservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("http://k9c108.p.ssafy.io:8000/user-service")
public interface UserServiceClient {

    @GetMapping("/client/v1/user-id")
    UserInfo searchUserInfo(@RequestHeader("Authorization") String token);

    @GetMapping("/client/v1/")
    UserInfo searchUserInfoByUserKey(@RequestParam(name = "otherUserKey") String otherUserKey);

}
