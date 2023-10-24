package com.everyschool.userservice.api.controller.codegroup;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/code-groups")
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCodeGroupResponse> createCodeGroup(@RequestBody CreateCodeGroupRequest request) {
        log.debug("call CodeGroupController#createCodeGroup");
        log.debug("CreateCodeGroupRequest={}", request);

        CreateCodeGroupResponse response = codeGroupService.createCodeGroup(request.getGroupName());
        log.debug("CreateCodeGroupResponse={}", response);

        return ApiResponse.created(response);
    }
}
