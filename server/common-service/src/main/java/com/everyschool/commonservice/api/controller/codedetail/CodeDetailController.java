package com.everyschool.commonservice.api.controller.codedetail;

import com.everyschool.commonservice.api.ApiResponse;
import com.everyschool.commonservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.commonservice.api.controller.codedetail.response.CodeDetailResponse;
import com.everyschool.commonservice.api.controller.codedetail.response.CodeResponse;
import com.everyschool.commonservice.api.controller.codedetail.response.CreateCodeDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    public ApiResponse<CodeDetailResponse> searchCodeDetails(@PathVariable Long groupId) {
        CodeResponse codeResponse1 =  CodeResponse.builder()
            .codeId(2L)
            .codeName("교장")
            .build();
        CodeResponse codeResponse2 =  CodeResponse.builder()
            .codeId(3L)
            .codeName("교감")
            .build();

        CodeDetailResponse response = CodeDetailResponse.builder()
            .groupId(1L)
            .groupName("직책")
            .codes(List.of(codeResponse1, codeResponse2))
            .build();

        return ApiResponse.ok(response);
    }

    public ApiResponse<?> editCodeDetail() {
        return null;
    }

    public ApiResponse<?> removeCodeDetail() {
        return null;
    }
}
