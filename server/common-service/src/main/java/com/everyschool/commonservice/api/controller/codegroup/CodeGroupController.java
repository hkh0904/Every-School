package com.everyschool.commonservice.api.controller.codegroup;

import com.everyschool.commonservice.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/common-service/groups")
public class CodeGroupController {

    public ApiResponse<?> createCodeGroup() {
        return null;
    }

    public ApiResponse<?> searchCodeGroups() {
        return null;
    }

    public ApiResponse<?> editCodeGroup() {
        return null;
    }

    public ApiResponse<?> removeCodeGroup() {
        return null;
    }
}
