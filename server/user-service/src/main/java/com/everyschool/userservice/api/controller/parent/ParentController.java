package com.everyschool.userservice.api.controller.parent;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.parent.request.ConnectStudentParentRequest;
import com.everyschool.userservice.api.controller.parent.response.CreateStudentParentResponse;
import com.everyschool.userservice.api.service.user.StudentParentService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class ParentController {

    private final StudentParentService studentParentService;
    private final TokenUtils tokenUtils;

    @PostMapping("/connection")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateStudentParentResponse> connectStudentParent(@RequestBody ConnectStudentParentRequest request) {
        log.debug("call ParentController#connectStudentParent");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        log.debug("connectCode={}", request.getConnectCode());

        CreateStudentParentResponse response = studentParentService.createStudentParent(userKey, request.getConnectCode());
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
