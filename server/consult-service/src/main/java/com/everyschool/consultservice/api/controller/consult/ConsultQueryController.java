package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/schools/{schoolId}/consults")
public class ConsultQueryController {

    private final ConsultQueryService consultQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<List<ConsultResponse>> searchConsults(@PathVariable String schoolId) {
        log.debug("call ConsultQueryController#searchConsults");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<ConsultResponse> responses = consultQueryService.searchConsults(userKey);
        log.debug("result={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/{consultId}")
    public ApiResponse<ConsultDetailResponse> searchConsult(@PathVariable String schoolId, @PathVariable Long consultId) {
        log.debug("call ConsultQueryController#searchConsult");
        log.debug("consultId={}", consultId);

        ConsultDetailResponse response = consultQueryService.searchConsult(consultId);
        log.debug("result={}", response);

        return ApiResponse.ok(response);
    }
}
