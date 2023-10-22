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

/**
 * 회원 인증 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 이메일 인증 API
     *
     * @param request 인증 번호를 전송할 이메일 정보
     * @return 200 OK null
     */
    @PostMapping("/email")
    public ApiResponse<String> authEmail(@RequestBody AuthEmailRequest request) {
        log.debug("call AuthController#authEmail");
        log.debug("AuthEmailRequest={}", request);

        EmailMessage message = request.toMessage();

        authService.sendEmail(message);

        return ApiResponse.of(HttpStatus.OK, "인증 번호 발송", null);
    }

    /**
     * 이메일 인증 번호 체크 API
     *
     * @param request 인증 번호를 전송 받은 이메일과 확인할 인증 번호
     * @return 200 OK null
     */
    @PostMapping("/email/check")
    public ApiResponse<String> authEmailCheck(@RequestBody AuthEmailCheckRequest request) {
        log.debug("call AuthController#authEmailCheck");
        log.debug("AuthEmailCheckRequest={}", request);

        authService.checkEmailAuthNumber(request.getEmail(), request.getAuthCode());

        return ApiResponse.of(HttpStatus.OK, "인증 번호 일치", null);
    }
}
