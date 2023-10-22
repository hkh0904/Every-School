package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.EditPwdRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotPwdRequest;
import com.everyschool.userservice.api.controller.user.request.JoinUserRequest;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> join(@Valid @RequestBody JoinUserRequest request) {
        log.debug("call UserController#join");
        log.debug("JoinUserRequest={}", request);

        UserResponse response = userService.createUser(request.toDto());
        log.debug("UserResponse={}", response);

        return ApiResponse.created(response);
    }

    @GetMapping("/{userKey}/info")
    public ApiResponse<UserInfoResponse> searchUserInfo(@PathVariable String userKey) {
        UserInfoResponse response = UserInfoResponse.builder()
            .type("학생")
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .birth("010101")
            .joinDate(LocalDateTime.now())
            .build();

        return ApiResponse.ok(response);
    }

    @PostMapping("/forgot")
    public ApiResponse<String> forgotEmail(@RequestBody ForgotEmailRequest request) {
        String response = "ssa**@ssafy.com";

        return ApiResponse.ok(response);
    }

    @PostMapping("/forgot/pwd")
    public ApiResponse<String> forgotPwd(@RequestBody ForgotPwdRequest request) {
        return ApiResponse.ok(null);
    }

    @PutMapping("/{userKey}/pwd")
    public ApiResponse<String> editPwd(@PathVariable String userKey, @RequestBody EditPwdRequest request) {
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{userKey}/withdrawal")
    public ApiResponse<WithdrawalResponse> withdrawal(@PathVariable String userKey) {
        WithdrawalResponse response = WithdrawalResponse.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .type("학생")
            .withdrawalDate(LocalDateTime.now())
            .build();

        return ApiResponse.ok(response);
    }
}
