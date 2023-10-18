package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.EditPwdRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.controller.user.request.JoinUserRequest;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> join(@RequestBody JoinUserRequest request) {
        UserResponse response = UserResponse.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .type("학생")
            .createdDate(LocalDateTime.now())
            .build();

        return ApiResponse.created(response);
    }

    public ApiResponse<?> createChild() {
        return null;
    }

    @PostMapping("/forgot")
    public ApiResponse<String> forgotEmail(@RequestBody ForgotEmailRequest request) {
        String response = "ssa**@ssafy.com";

        return ApiResponse.ok(response);
    }

    public ApiResponse<?> forgetPassword() {
        return null;
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
