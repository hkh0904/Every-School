package com.everyschool.callservice.api.controller.donotdisturb;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.DoNotDisturbQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/do-not-disturbs")
public class DoNotDisturbQueryController {

    private final DoNotDisturbQueryService doNotDisturbQueryService;

    /**
     * (교사용) 방해 금지 타임 조회 API
     *
     * @return 요청 정보 리스트
     */
    @GetMapping("/")
    public ApiResponse<List<DoNotDisturbResponse>> searchDoNotDisturbs(@RequestHeader("Authorization") String token) {
        log.debug("call DoNotDisturbQueryController#searchDoNotDisturbs");
        log.debug("token={}", token);

        List<DoNotDisturbResponse> responses = doNotDisturbQueryService.searchMyDoNotDisturbs(token);
        log.debug("search results = {}", responses);

        return ApiResponse.ok(responses);
    }
}
