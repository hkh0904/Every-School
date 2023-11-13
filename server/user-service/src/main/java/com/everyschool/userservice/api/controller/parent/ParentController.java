package com.everyschool.userservice.api.controller.parent;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.parent.request.ConnectStudentParentRequest;
import com.everyschool.userservice.api.controller.parent.response.CreateStudentParentResponse;
import com.everyschool.userservice.api.service.user.StudentParentService;
import com.everyschool.userservice.messagequeue.KafkaProducer;
import com.everyschool.userservice.messagequeue.dto.ParentSchoolApplyDto;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class ParentController {

    private final StudentParentService studentParentService;
    private final KafkaProducer kafkaProducer;
    private final TokenUtils tokenUtils;

    @PostMapping("/connection")
    public ApiResponse<ParentSchoolApplyDto> connectStudentParent(
        @Valid @RequestBody ConnectStudentParentRequest request
    ) {
        log.debug("call ParentController#connectStudentParent");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        log.debug("connectCode={}", request.getConnectCode());

        ParentSchoolApplyDto dto = studentParentService.checkStudentParent(userKey, request.getConnectCode());
        log.debug("result={}", dto);

        kafkaProducer.parentSchoolApply("parent-school-apply", dto);

        return ApiResponse.ok(null);
    }
}
