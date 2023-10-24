package com.everyschool.userservice.api.controller.codedetail;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.userservice.api.controller.codedetail.respnse.CreateCodeDetailResponse;
import com.everyschool.userservice.api.controller.codedetail.respnse.RemoveCodeDetailResponse;
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
        log.debug("call CodeDetailController#createCodeDetail");
        log.debug("groupId={}", groupId);
        log.debug("CreateCodeDetailRequest={}", request);

        CreateCodeDetailResponse response = codeDetailService.createCodeDetail(groupId, request.getCodeName());
        log.debug("CreateCodeDetailResponse={}", response);

        return ApiResponse.created(response);
    }

    @DeleteMapping("{codeId}")
    public ApiResponse<RemoveCodeDetailResponse> removeCodeDetail(@PathVariable Integer groupId, @PathVariable Integer codeId) {
        log.debug("call CodeDetailController#createCodeDetail");
        log.debug("groupId={}", groupId);
        log.debug("codeId={}", codeId);

        RemoveCodeDetailResponse response = codeDetailService.removeCodeDetail(codeId);
        log.debug("RemoveCodeDetailResponse={}", response);

        return ApiResponse.ok(response);
    }
}
