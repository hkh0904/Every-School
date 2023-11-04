package com.everyschool.consultservice.api.client;

import com.everyschool.consultservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchUserInfo(@PathVariable(name = "userKey") String userKey);
}
