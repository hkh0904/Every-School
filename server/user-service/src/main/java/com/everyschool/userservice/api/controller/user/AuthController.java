package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.client.mail.dto.EmailMessage;
import com.everyschool.userservice.api.controller.user.request.AuthEmailCheckRequest;
import com.everyschool.userservice.api.controller.user.request.AuthEmailRequest;
import com.everyschool.userservice.api.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email")
    public ApiResponse<String> authEmail(@RequestBody AuthEmailRequest request) {
        EmailMessage message = request.toMessage();

        authService.sendEmail(message);

        return ApiResponse.of(HttpStatus.OK, "인증 번호 발송", null);
    }

    @PostMapping("/email/check")
    public ApiResponse<String> authEmailCheck(@RequestBody AuthEmailCheckRequest request) {
        return ApiResponse.ok(null);
    }
}
