package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotPwdRequest;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/forgot")
public class AccountController {

    private final UserService userService;
    private final UserQueryService userQueryService;

    @PostMapping("/email")
    public ApiResponse<String> forgotEmail(@Valid @RequestBody ForgotEmailRequest request) {
        log.debug("call AccountController#forgotEmail");
        log.debug("ForgotEmailRequest={}", request);

        String maskingEmail = userQueryService.searchEmail(request.getName(), request.getBirth());
        log.debug("maskingEmail={}", maskingEmail);

        return ApiResponse.ok(maskingEmail);
    }

    @PostMapping("/pwd")
    public ApiResponse<String> forgotPwd(@Valid @RequestBody ForgotPwdRequest request) {
        log.debug("call AccountController#forgotPwd");
        log.debug("ForgotPwdRequest={}", request);

        String randomPwd = userService.editRandomPwd(request.getEmail(), request.getName(), request.getBirth());
        log.debug("randomPwd={}", randomPwd);

        return ApiResponse.of(HttpStatus.OK, "비밀번호가 초기화 되었습니다.", null);
    }
}
