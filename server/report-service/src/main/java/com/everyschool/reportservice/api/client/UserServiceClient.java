package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface UserServiceClient {

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    UserInfo searchByUserKey(@RequestBody String userKey);
}
