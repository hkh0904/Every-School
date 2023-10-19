package com.everyschool.commonservice.api.controller.codegroup;

import com.everyschool.commonservice.api.ApiResponse;
import com.everyschool.commonservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.commonservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
