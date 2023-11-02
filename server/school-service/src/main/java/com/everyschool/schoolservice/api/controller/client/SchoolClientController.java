package com.everyschool.schoolservice.api.controller.client;


import com.everyschool.schoolservice.api.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/client/v1")
public class SchoolClientController {

    private final SchoolUserQueryService schoolUserQueryService;

    @PostMapping("/student-info")
    public StudentInfo searchByUserId(@RequestBody Long userId) {
        log.debug("call SchoolClientController#searchByUserId");
        log.debug("userId={}", userId);

        StudentInfo studentInfo = schoolUserQueryService.searchStudentInfo(userId);
        log.debug("studentInfo={}", studentInfo);

        return studentInfo;
    }
}
