package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.*;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.ParentService;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 회원 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final ParentService parentService;
    private final StudentService studentService;

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

    @PatchMapping("/v1/pwd")
    public ApiResponse<String> editPwd(@RequestBody EditPwdRequest request) {
        // TODO: 2023-10-25 임우택 JWT 복호화 기능 구현
        String email = "ssafy@gmail.com";

        UserResponse response = userService.editPwd(email, request.getCurrentPwd(), request.getNewPwd());

        return ApiResponse.of(HttpStatus.OK, "비밀번호가 변경되었습니다.", null);
    }

    @PostMapping("/v1/withdrawal")
    public ApiResponse<WithdrawalResponse> withdrawal(@RequestBody WithdrawalRequest request) {
        // TODO: 2023-10-25 임우택 JWT 복호화 기능 구현
        String email = "ssafy@gmail.com";

        WithdrawalResponse response = userService.withdrawal(email, request.getPwd());

        return ApiResponse.of(HttpStatus.OK, "회원 탈퇴가 되었습니다.", response);
    }
}
