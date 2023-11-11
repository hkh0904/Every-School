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

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/do-not-disturbs")
public class DoNotDisturbQueryController {

    private final DoNotDisturbQueryService doNotDisturbQueryService;

    /**
     * (교사용) 방해 금지 타임 조회 API
     *
     * @return 가장 최근에 등록한 방해 금지 타임
     */
    @GetMapping("/")
    public ApiResponse<DoNotDisturbResponse> searchDoNotDisturb(@RequestHeader("Authorization") String token) {
        log.debug("call DoNotDisturbQueryController#searchDoNotDisturb");
        log.debug("token={}", token);

        DoNotDisturbResponse response = doNotDisturbQueryService.searchMyDoNotDisturb(token);
        log.debug("search results = {}", response);

        return ApiResponse.ok(response);
    }
}
