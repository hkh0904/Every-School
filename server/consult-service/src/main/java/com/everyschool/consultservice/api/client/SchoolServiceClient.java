package com.everyschool.consultservice.api.client;

import com.everyschool.consultservice.api.client.response.StudentSchoolClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    // TODO: 2023-11-09 학생 아이디와 학년도로 해당 학생의 학번을 조회
    @PostMapping("/school-service/client/v1/student-info")
    StudentSchoolClassInfo searchByUserId(@RequestBody Long userId);

    @GetMapping("/school-service/client/v1/classes/{schoolClassId}/teachers")
    Long searchTeacherId(@PathVariable(name = "schoolClassId") Long schoolClassId);
}
