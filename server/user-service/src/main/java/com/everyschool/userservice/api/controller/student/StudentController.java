package com.everyschool.userservice.api.controller.student;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class StudentController {

    private final StudentService studentService;
    private final TokenUtils tokenUtils;

    @GetMapping("/connection")
    public ApiResponse<String> generateConnectCode() {
        String userKey = tokenUtils.getUserKey();

        String connectCode = studentService.generateConnectCode(userKey);

        return ApiResponse.ok(connectCode);
    }
}
