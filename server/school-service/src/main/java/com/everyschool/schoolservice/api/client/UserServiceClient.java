package com.everyschool.schoolservice.api.client;

import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchUserInfo(@PathVariable(name = "userKey") String userKey);

    @PostMapping
    List<StudentResponse> searchByStudentIdIn(@RequestBody List<Long> studentIds);
}
