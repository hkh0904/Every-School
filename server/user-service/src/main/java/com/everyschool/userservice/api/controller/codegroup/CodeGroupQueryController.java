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

/**
 * 코드 그룹 Query API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/code-groups")
public class CodeGroupQueryController {

    private final CodeGroupQueryService codeGroupQueryService;

    /**
     * 코드 그룹 목록 조회 APi
     *
     * @return 코드 그룹 전체 목록
     */
    @GetMapping
    public ApiResponse<List<CodeGroupResponse>> searchCodeGroups() {
        log.debug("call CodeGroupQueryController#searchCodeGroups");

        List<CodeGroupResponse> responses = codeGroupQueryService.searchCodeGroups();
        log.debug("responses={}", responses);

        return ApiResponse.ok(responses);
    }
}
