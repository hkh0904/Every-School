package com.everyschool.chatservice.api.client;

import com.everyschool.chatservice.api.client.response.SchoolClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    @PostMapping("/school-service/client/v1/school-class-info")
    SchoolClassInfo searchSchoolClassInfo(@RequestBody Long schoolClassId);
}
