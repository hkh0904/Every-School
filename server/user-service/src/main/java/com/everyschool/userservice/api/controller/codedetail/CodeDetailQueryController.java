package com.everyschool.userservice.api.controller.codedetail;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeResponse;
import com.everyschool.userservice.api.service.codedetail.CodeDetailQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/code-groups/{groupId}/code-details")
public class CodeDetailQueryController {

    private final CodeDetailQueryService codeDetailQueryService;

    @GetMapping
    public ApiResponse<CodeResponse> searchCodeDetails(@PathVariable Integer groupId) {
        CodeResponse response = codeDetailQueryService.searchCodeDetails(groupId);
        return ApiResponse.ok(response);
    }
}
