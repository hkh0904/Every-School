package com.everyschool.schoolservice.api.app.controller.schoolapply;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/app/{schoolYear}/schools/{schoolId}/applies")
public class SchoolApplyAppQueryController {

    private final SchoolApplyAppQueryService schoolApplyAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/exist")
    public ApiResponse<String> existApply(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        boolean result = schoolApplyAppQueryService.existApply(userKey);

        if (result) {
            return ApiResponse.of(HttpStatus.OK, "승인 대기 중입니다.", null);
        }

        return ApiResponse.ok(null);
    }
}
