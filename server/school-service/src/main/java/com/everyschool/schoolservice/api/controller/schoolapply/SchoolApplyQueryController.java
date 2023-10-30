package com.everyschool.schoolservice.api.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools/{schoolId}/apply")
public class SchoolApplyQueryController {

    private final SchoolApplyQueryService schoolApplyQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<List<SchoolApplyResponse>> searchSchoolApplies(@PathVariable Long schoolId, @RequestParam(defaultValue = "wait") String status) {
        log.debug("call SchoolApplyQueryController#searchSchoolApplies");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<SchoolApplyResponse> responses = schoolApplyQueryService.searchSchoolApplies(userKey, status);
        log.debug("result={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/{schoolApplyId}")
    public ApiResponse<SchoolApplyResponse> searchSchoolApply(@PathVariable Long schoolId, @PathVariable Long schoolApplyId) {
        log.debug("call SchoolApplyQueryController#searchSchoolApply");

        SchoolApplyResponse response = schoolApplyQueryService.searchSchoolApply(schoolApplyId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
