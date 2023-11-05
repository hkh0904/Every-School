package com.everyschool.consultservice.api.client;

import com.everyschool.consultservice.api.client.response.ConsultUserInfo;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    SchoolClassInfo searchSchoolClassByTeacherId(@RequestBody Long teacherId);

    @PostMapping("/school-service/client/v1/consult-user-infos")
    List<ConsultUserInfo> searchConsultUser(@RequestBody List<Long> userIds);
}
