package com.everyschool.commonservice.api.controller.codedetail;

import com.everyschool.commonservice.api.ApiResponse;
import com.everyschool.commonservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.commonservice.api.controller.codedetail.response.CreateCodeDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/common-service/groups/{groupId}/codes")
public class CodeDetailController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCodeDetailResponse> createCodeDetail(@PathVariable Long groupId, @RequestBody CreateCodeDetailRequest request) {
        CreateCodeDetailResponse response = CreateCodeDetailResponse.builder()
            .codeId(3L)
            .groupName("직책")
            .codeName("교장")
            .createdDate(LocalDateTime.now())
            .build();

        return ApiResponse.created(response);
    }

    public ApiResponse<?> searchCodeDetails() {
        return null;
    }

    public ApiResponse<?> editCodeDetail() {
        return null;
    }

    public ApiResponse<?> removeCodeDetail() {
        return null;
    }
}
