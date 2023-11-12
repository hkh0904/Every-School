package com.everyschool.schoolservice.api.client;

import com.everyschool.schoolservice.api.client.response.StudentParentInfo;
import com.everyschool.schoolservice.api.client.response.UserResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchUserInfo(@PathVariable(name = "userKey") String userKey);

    @PostMapping("/user-service/client/v1/student-info")
    List<UserResponse> searchByStudentIdIn(@RequestBody List<Long> studentIds);

    @GetMapping("/user-service/client/v1/student-parent/{schoolClassId}")
    List<StudentParentInfo> searchStudentParentBySchoolClassId(@PathVariable(name = "schoolClassId") Long schoolClassId);

    @GetMapping("/user-service/client/v1/user-info/{userId}/user-id")
    UserInfo searchUserInfoById(@PathVariable(name = "userId") Long userId);

    @GetMapping("/user-service/client/v1/user-info/{userId}/user-response")
    UserResponse searchUserById(@PathVariable(name = "userId") Long userId);
}
