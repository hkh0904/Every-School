package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchByUserKey(@PathVariable(name = "userKey") String userKey);

    @GetMapping("/user-service/client/v1/user-info/{userId}/user-id")
    UserInfo searchByUserId(@PathVariable(name = "userId") Long userId);
}
