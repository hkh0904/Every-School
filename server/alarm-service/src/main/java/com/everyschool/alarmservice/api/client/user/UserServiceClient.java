package com.everyschool.alarmservice.api.client.user;

import com.everyschool.alarmservice.api.client.user.response.UserInfo;
import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

//    @PostMapping("/join/student")
//    List<Long> searchUserIds(@RequestBody UserIdRequest request);
    @GetMapping("/user-service/client/v1/user-info")
    UserInfo searchUserInfo(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchUserInfoByUserKey(@PathVariable(name = "userKey") String userKey);

    @GetMapping("/user-service/client/v1/user-fcm-info/{userKey}")
    String searchUserFcmByUserKey(@PathVariable(name = "userKey") String userKey);
}
