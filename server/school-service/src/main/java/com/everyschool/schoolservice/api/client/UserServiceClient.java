package com.everyschool.schoolservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface UserServiceClient {

    @PostMapping
    Long searchByUserKey(@RequestBody String userKey);
}
