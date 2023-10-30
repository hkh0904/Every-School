package com.everyschool.consultservice.api.client;

import com.everyschool.consultservice.api.client.response.TeacherInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    Long searchByUserKey(@RequestBody String userKey);

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    List<TeacherInfo> searchTeacherByIdIn(@RequestBody List<Long> teacherIds);

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    TeacherInfo searchTeacherById(@RequestBody Long teacherId);

    // TODO: 2023-10-30 상대 구현
    @PostMapping
    TeacherInfo searchTeacherByUserKey(@RequestBody String userKey);
}
