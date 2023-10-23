package com.everyschool.userservice.api.controller.codedetail;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.userservice.api.controller.codedetail.respnse.CreateCodeDetailResponse;
import com.everyschool.userservice.api.service.codedetail.CodeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/code-groups/{groupId}/code-details")
public class CodeDetailController {

    private final CodeDetailService codeDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCodeDetailResponse> createCodeDetail(
        @PathVariable Integer groupId,
        @RequestBody CreateCodeDetailRequest request
    ) {
        CreateCodeDetailResponse response = codeDetailService.createCodeDetail(groupId, request.getCodeName());
        return ApiResponse.created(response);
    }
}
