package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.StudentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("school-service")
public interface SchoolServiceClient {

    @GetMapping("/school-service/client/v1/student-info/{userId}")
    StudentInfo searchByUserId(@PathVariable String userId);
}
