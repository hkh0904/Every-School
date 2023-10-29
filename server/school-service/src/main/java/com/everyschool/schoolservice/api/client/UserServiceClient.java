package com.everyschool.schoolservice.api.client;

import com.everyschool.schoolservice.api.client.response.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @PostMapping
    Long searchByUserKey(@RequestBody String userKey);

    @PostMapping
    List<StudentResponse> searchByStudentIdIn(@RequestBody List<Long> studentIds);
}
