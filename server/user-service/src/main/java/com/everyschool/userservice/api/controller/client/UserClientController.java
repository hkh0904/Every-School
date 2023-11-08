package com.everyschool.userservice.api.controller.client;

import com.everyschool.userservice.api.controller.client.response.StudentParentInfo;
import com.everyschool.userservice.api.controller.client.response.StudentResponse;
import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.service.user.StudentParentQueryService;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MSA 통신용 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/client/v1")
public class UserClientController {

    private final UserQueryService userQueryService;
    private final StudentParentQueryService studentParentQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 토큰으로 회원 정보 조회 API
     *
     * @return 회원 정보
     */
    @GetMapping("/user-info")
    public UserInfo searchUserInfo() {
        log.debug("call UserClientController#searchUserInfo");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        UserInfo userInfo = userQueryService.searchUserInfo(userKey);
        log.debug("UserInfo={}", userInfo);

        return userInfo;
    }

    /**
     * 고유키로 회원 정보 조회 API
     *
     * @return 회원 정보
     */
    @GetMapping("/user-info/{userKey}")
    public UserInfo searchUserInfo(@PathVariable String userKey) {
        log.debug("call UserClientController#searchUserInfo");

        UserInfo userInfo = userQueryService.searchUserInfo(userKey);
        log.debug("UserInfo={}", userInfo);

        return userInfo;
    }

    /**
     * 학생 PK로 학생 정보 조회 API
     *
     * @param studentIds 학생 PK 리스트
     * @return 학생 정보
     */
    @PostMapping("/student-info")
    public List<StudentResponse> searchByStudentIdIn(@RequestBody List<Long> studentIds) {
        log.debug("call UserClientController#searchByStudentIdIn");

        List<StudentResponse> response = userQueryService.searchByStudentIdIn(studentIds);
        log.debug("results={}", response);

        return response;
    }

    /**
     * 학급 PK로 가족 관계 조회
     *
     * @param schoolClassId 학급 PK
     * @return 조회된 가족 관계 정보
     */
    @GetMapping("/student-parent/{schoolClassId}")
    public List<StudentParentInfo> searchStudentParentBySchoolClassId(@PathVariable Long schoolClassId) {
        log.debug("call UserClientController#searchStudentParentBySchoolClassId");

        List<StudentParentInfo> infos = studentParentQueryService.searchStudentParentBySchoolClassId(schoolClassId);
        log.debug("results={}", infos);

        return infos;
    }

    @GetMapping("/user-info/{userId}/user-id")
    public UserInfo searchUserInfoById(@PathVariable Long userId) {

        // TODO: 2023-11-08

        return null;
    }
}
