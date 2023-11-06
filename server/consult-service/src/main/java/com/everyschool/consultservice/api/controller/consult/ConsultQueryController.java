package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.Result;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.WebConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults")
public class ConsultQueryController {

    private final ConsultQueryService consultQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<Result<WebConsultResponse>> searchConsults(
        @PathVariable Integer schoolYear,
        @PathVariable String schoolId,
        @RequestParam Integer status
    ) {
        log.debug("call ConsultQueryController#searchConsults");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<WebConsultResponse> response = consultQueryService.searchConsults(userKey, schoolYear, status);
        log.debug("results={}", response);

        return ApiResponse.ok(Result.of(response));
    }

    @GetMapping("/{consultId}")
    public ApiResponse<ConsultDetailResponse> searchConsult(
        @PathVariable Integer schoolYear,
        @PathVariable String schoolId,
        @PathVariable Long consultId
    ) {
        log.debug("call ConsultQueryController#searchConsults");

        ConsultDetailResponse response = consultQueryService.searchConsult(consultId);

        return ApiResponse.ok(response);
    }
}
