package com.everyschool.consultservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    // TODO: 2023-11-09 학생 아이디와 학년도로 해당 학생의 학번을 조회
    @PostMapping("/{schoolId}/{schoolYear}")
    Integer searchStudentNumber(@PathVariable(name = "schoolId") Long schoolId, @PathVariable(name = "schoolYear") Integer schoolYear);

}
