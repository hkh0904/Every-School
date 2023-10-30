package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/consults")
public class ConsultQueryController {

    private final ConsultQueryService consultQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<List<ConsultResponse>> searchConsults() {
        log.debug("call ConsultQueryController#searchConsults");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<ConsultResponse> responses = consultQueryService.searchConsults(userKey);
        log.debug("result={}", responses);

        return ApiResponse.ok(responses);
    }
}
