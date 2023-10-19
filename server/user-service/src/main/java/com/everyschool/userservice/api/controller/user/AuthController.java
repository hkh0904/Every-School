package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.AuthEmailCheckRequest;
import com.everyschool.userservice.api.controller.user.request.AuthEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/email")
    public ApiResponse<String> authEmail(@RequestBody AuthEmailRequest request) {
        return ApiResponse.ok(null);
    }

    @PostMapping("/email/check")
    public ApiResponse<String> authEmailCheck(@RequestBody AuthEmailCheckRequest request) {
        return ApiResponse.ok(null);
    }
}
