package com.everyschool.consultservice.api.client;

import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("school-service")
public interface SchoolServiceClient {

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    SchoolClassInfo searchSchoolClassByTeacherId(@RequestBody Long teacherId);
}
