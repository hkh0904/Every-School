package com.everyschool.userservice.api.controller.codegroup;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.controller.codegroup.response.RemoveCodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 코드 그룹 Command API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/code-groups")
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    /**
     * 코드 그룹 생성 API
     *
     * @param request 등록할 코드 그룹 정보
     * @return 등록된 코드 그룹 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCodeGroupResponse> createCodeGroup(@Valid @RequestBody CreateCodeGroupRequest request) {
        log.debug("call CodeGroupController#createCodeGroup");
        log.debug("CreateCodeGroupRequest={}", request);

        CreateCodeGroupResponse response = codeGroupService.createCodeGroup(request.getGroupName());
        log.debug("CreateCodeGroupResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 코드 그룹 삭제 API
     *
     * @param groupId 코드 그룹 Id
     * @return 삭제된 코드 그룹 정보
     */
    @DeleteMapping("/{groupId}")
    public ApiResponse<RemoveCodeGroupResponse> removeCodeGroup(@PathVariable Integer groupId) {
        log.debug("call CodeGroupController#removeCodeGroup");
        log.debug("remove groupId={}", groupId);

        RemoveCodeGroupResponse response = codeGroupService.removeCodeGroup(groupId);
        log.debug("RemoveCodeGroupResponse={}", response);

        return ApiResponse.ok(response);
    }
}
