package com.everyschool.callservice.api.controller.usercall;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.call.UserCallQueryService;
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
public class UserCallQueryController {

    private final UserCallQueryService userCallQueryService;

    /**
     * 내 통화 내역 조회 API
     *
     * @return 통화 내역 리스트
     */
    @GetMapping("/{userKey}")
    public ApiResponse<List<UserCallResponse>> searchMyCalls(@PathVariable String userKey) {
        log.debug("call UserCallQueryController#searchMyCalls");
        log.debug("userKey={}", userKey);

        List<UserCallResponse> responses = userCallQueryService.searchMyCalls(userKey);
        log.debug("search results = {}", responses);

        return ApiResponse.ok(responses);
    }

    /**
     * 내 통화 단건 조회 API
     *
     * @return 통화
     */
//    @GetMapping("/detail/{callId}")
//    public ApiResponse<List<UserCallResponse>> searchCallDetails(@PathVariable Long callId) {
//        log.debug("call UserCallQueryController#searchMyCall");
//
//        UserCallResponse response = callQueryService.searchMyCall(callId);
//        log.debug("search results = {}", response);
//
//        return ApiResponse.ok(response);
//    }

}
