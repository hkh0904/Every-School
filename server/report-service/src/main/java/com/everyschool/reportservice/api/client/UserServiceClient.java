package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchByUserKey(@PathVariable String userKey);

    // TODO: 2023-11-02 구현 예정
    @GetMapping("/user-service/client/v1/user-info/{userId}")
    UserInfo searchByUserId(@PathVariable(name = "userId") Long userId);
}
