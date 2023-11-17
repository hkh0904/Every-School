package com.everyschool.chatservice.api.client;

import com.everyschool.chatservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userId}/user-id")
    UserInfo searchUserInfoById(@PathVariable(name = "userId") Long userId);

    @GetMapping("/user-service/client/v1/user-info")
    UserInfo searchUserInfo(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchUserInfoByUserKey(@PathVariable(name = "userKey") String userKey);

    @GetMapping("/user-service/client/v1/user/{schoolClassId}/name/{parentId}")
    String searchUsername(@PathVariable(name = "schoolClassId") Long schoolClassId, @PathVariable(name = "parentId") Long parentId);

    @GetMapping("/user-service/client/v1/user-fcm-info/{userId}/user-id")
    String searchFcmTokenByUserId(@PathVariable Long userId);
}
