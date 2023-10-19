package com.everyschool.commonservice.api.controller.codegroup;

import com.everyschool.commonservice.api.ApiResponse;
import com.everyschool.commonservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.commonservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.commonservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/common-service/groups")
public class CodeGroupController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCodeGroupResponse> createCodeGroup(@RequestBody CreateCodeGroupRequest request) {
        CreateCodeGroupResponse response = CreateCodeGroupResponse.builder()
            .groupId(1L)
            .name("직책")
            .createdDate(LocalDateTime.now())
            .build();

        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<List<CodeGroupResponse>> searchCodeGroups() {
        CodeGroupResponse response1 = CodeGroupResponse.builder()
            .groupId(1L)
            .name("직책")
            .build();
        CodeGroupResponse response2 = CodeGroupResponse.builder()
            .groupId(2L)
            .name("카테고리")
            .build();
        List<CodeGroupResponse> responses = List.of(response1, response2);

        return ApiResponse.ok(responses);
    }

    public ApiResponse<?> editCodeGroup() {
        return null;
    }

    public ApiResponse<?> removeCodeGroup() {
        return null;
    }
}
