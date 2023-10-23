package com.everyschool.userservice.api.controller.codegroup;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/code-groups")
public class CodeGroupQueryController {

    private final CodeGroupQueryService codeGroupQueryService;

    @GetMapping
    public ApiResponse<List<CodeGroupResponse>> searchCodeGroups() {
        List<CodeGroupResponse> responses = codeGroupQueryService.searchCodeGroups();
        return ApiResponse.ok(responses);
    }
}
