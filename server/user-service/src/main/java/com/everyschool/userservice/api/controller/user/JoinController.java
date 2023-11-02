package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.JoinParentRequest;
import com.everyschool.userservice.api.controller.user.request.JoinStudentRequest;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.ParentService;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.api.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class JoinController {

    private final ParentService parentService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    /**
     * 학부모 회원 가입 API
     *
     * @param request 회원 가입시 필요한 회원 정보
     * @return 가입에 성공한 회원의 기본 정보
     */
    @PostMapping("/join/parent")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> joinParent(@Valid @RequestBody JoinParentRequest request) {
        log.debug("call UserController#joinParent");
        log.debug("JoinParentRequest={}", request);

        UserResponse response = parentService.createParent(request.toDto(), request.getParentType());
        log.debug("UserResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 학생 회원 가입 API
     *
     * @param request 회원 가입시 필요한 회원 정보
     * @return 가입에 성공한 회원의 기본 정보
     */
    @PostMapping("/join/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> joinStudent(@Valid @RequestBody JoinStudentRequest request) {
        log.debug("call UserController#joinStudent");
        log.debug("JoinStudentRequest={}", request);

        UserResponse response = studentService.createStudent(request.toDto());
        log.debug("UserResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 교직원 회원 가입 API
     *
     * @param request 회원 가입시 필요한 회원 정보
     * @return 가입에 성공한 회원의 기본 정보
     */
    @PostMapping("/join/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> joinTeacher(@Valid @RequestBody JoinStudentRequest request) {
        log.debug("call UserController#joinTeacher");
        log.debug("JoinStudentRequest={}", request);

        UserResponse response = teacherService.createTeacher(request.toDto());
        log.debug("UserResponse={}", response);

        return ApiResponse.created(response);
    }
}
