package com.everyschool.userservice.api.client.school;

import com.everyschool.userservice.api.client.school.response.DescendantInfo;
import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    @PostMapping("/school-service/client/v1/school-class-info")
    SchoolClassInfo searchBySchoolClassId(@RequestBody Long schoolClassId);

    @PostMapping("/school-service/client/v1/descendant-info")
    List<DescendantInfo> searchByUserId(@RequestBody List<Long> userId);
}
