package com.everyschool.schoolservice.api.web.controller.client;

import com.everyschool.schoolservice.api.service.schoolclass.SchoolClassQueryService;
import com.everyschool.schoolservice.api.web.controller.client.response.ConsultUserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.DescendantInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.SchoolClassInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/client/v1")
public class SchoolClientController {

    private final SchoolClassQueryService schoolClassQueryService;
    private final SchoolUserQueryService schoolUserQueryService;

    @PostMapping("/student-info")
    public StudentInfo searchByUserId(@RequestBody Long userId) {
        log.debug("call SchoolClientController#searchByUserId");
        log.debug("userId={}", userId);

        StudentInfo studentInfo = schoolUserQueryService.searchStudentInfo(userId);
        log.debug("studentInfo={}", studentInfo);

        return studentInfo;
    }

    @PostMapping("/consult-user-infos")
    public List<ConsultUserInfo> searchConsultUser(@RequestBody List<Long> userIds) {
        log.debug("call SchoolClientController#searchConsultUser");
        log.debug("userIds size={}", userIds.size());

        List<ConsultUserInfo> consultUserInfos = schoolUserQueryService.searchConsultUser(userIds);
        log.debug("results size={}", consultUserInfos.size());

        return consultUserInfos;
    }

    @PostMapping("/school-class-info")
    public SchoolClassInfo searchBySchoolClassId(@RequestBody Long schoolClassId) {

        SchoolClassInfo schoolClassInfo = schoolClassQueryService.searchBySchoolClassId(schoolClassId);
        log.debug("result={}", schoolClassInfo);

        return schoolClassInfo;
    }

    @PostMapping("/descendant-info")
    public List<DescendantInfo> searchByUserId(@RequestBody List<Long> userIds) {

        List<DescendantInfo> descendantInfos = schoolUserQueryService.searchDescendantInfo(userIds);
        log.debug("result={}", descendantInfos);

        return descendantInfos;
    }
}
