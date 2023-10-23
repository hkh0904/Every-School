package com.everyschool.userservice.api.controller.codegroup;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/code-groups")
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @PostMapping
    public ApiResponse<CreateCodeGroupResponse> createCodeGroup(CreateCodeGroupRequest request) {
        return ApiResponse.created(null);
    }
}
