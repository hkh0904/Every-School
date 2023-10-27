package com.everyschool.callservice.api.controller.call;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.CallQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/calls")
public class CallQueryController {

    private final CallQueryService callQueryService;

    /**
     * 내 통화 내역 조회 API
     *
     * @return 통화 내역 리스트
     */
    @GetMapping("/{userKey}")
    public ApiResponse<List<CallResponse>> searchMyCalls(@PathVariable String userKey) {
        log.debug("call CallQueryController#searchMyCalls");
        log.debug("userKey={}", userKey);

        List<CallResponse> responses = callQueryService.searchMyCalls(userKey);
        log.debug("search results = {}", responses);

        return ApiResponse.ok(responses);
    }

}
