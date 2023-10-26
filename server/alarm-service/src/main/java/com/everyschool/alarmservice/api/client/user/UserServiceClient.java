package com.everyschool.alarmservice.api.client.user;

import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/join/student")
    List<Long> searchUserIds(@RequestBody UserIdRequest request);
}
