package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    public ApiResponse<?> join() {
        return null;
    }

    public ApiResponse<?> createChild() {
        return null;
    }

    public ApiResponse<?> forgetEmail() {
        return null;
    }

    public ApiResponse<?> forgetPassword() {
        return null;
    }

    public ApiResponse<?> editPassword() {
        return null;
    }

    public ApiResponse<?> withdrawal() {
        return null;
    }
}
