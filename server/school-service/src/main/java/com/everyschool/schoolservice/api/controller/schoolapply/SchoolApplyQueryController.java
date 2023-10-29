package com.everyschool.schoolservice.api.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools/{schoolId}/apply")
public class SchoolApplyQueryController {

    private final SchoolApplyQueryService schoolApplyQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<SchoolApplyResponse> searchSchoolApplies(@PathVariable Long schoolId, @RequestParam(defaultValue = "wait") String status) {

        String userKey = tokenUtils.getUserKey();





        return null;
    }

    @GetMapping("/{schoolApplyId}")
    public ApiResponse<?> searchSchoolApply(@PathVariable Long schoolId, @PathVariable Long schoolApplyId) {
        return null;
    }
}
