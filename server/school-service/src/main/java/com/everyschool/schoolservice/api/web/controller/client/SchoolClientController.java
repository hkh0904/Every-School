package com.everyschool.schoolservice.api.web.controller.client;

import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyQueryService;
import com.everyschool.schoolservice.api.service.schoolclass.SchoolClassQueryService;
import com.everyschool.schoolservice.api.web.controller.client.response.*;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/client/v1")
public class SchoolClientController {

    private final SchoolApplyQueryService schoolApplyQueryService;
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

    @GetMapping("/student-info/{userId}")
    public StudentInfo searchByUserIdPath(@PathVariable Long userId) {
        log.debug("call SchoolClientController#searchByUserIdPath");
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

    @GetMapping("/infos/{schoolYear}/users/{userId}")
    public Long searchTeacherByUserId(@PathVariable Long userId, @PathVariable Integer schoolYear) {

        Long teacherId = schoolUserQueryService.searchTeacherByUserId(userId, schoolYear);
        log.debug("result={}", teacherId);

        return teacherId;
    }

    @GetMapping("/infos/{schoolYear}/users/{userId}/students")
    public List<StudentInfoCon> searchStudentsByUserId(@PathVariable Long userId, @PathVariable Integer schoolYear) {

        List<StudentInfoCon> infos = schoolUserQueryService.searchStudentsByUserId(userId, schoolYear);
        log.debug("results={}", infos);

        return infos;
    }

    @GetMapping("/applies/{studentId}/exist")
    public boolean existApply(@PathVariable Long studentId) {

        boolean result = schoolApplyQueryService.existApply(studentId);
        log.debug("result={}", result);

        return result;
    }

    @GetMapping("/classes/{schoolClassId}/teachers")
    public Long searchTeacherId(@PathVariable Long schoolClassId) {

        Long teacherId = schoolClassQueryService.searchTeacherId(schoolClassId);
        log.debug("teacherId={}", teacherId);

        return teacherId;
    }
}
