package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.StudentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("school-service")
public interface SchoolServiceClient {

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    StudentInfo searchByUserId(@RequestBody Long userId);
}
