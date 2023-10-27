package com.everyschool.callservice.api.controller.call;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.call.request.CreateCallRequest;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.CallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/calls")
public class CallController {

    private final CallService callService;

    /**
     * 통화 내역 등록 API
     *
     * @param request 통화 종료후 통화 내역 정보
     * @return 생성 완료 메세지
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createCallInfo(@RequestBody CreateCallRequest request,
                                              @RequestHeader("Authorization") String token) {
        log.debug("Call CallController#createCallInfo");
        log.debug("CreateCallRequest={}", request);

        CallResponse response = callService.createCallInfo(request.toDto(), request.getOtherUserKey(), token);
        log.debug("SavedCallResponse={}", response);

        return ApiResponse.created("통화 내용 저장 완료.");
    }
}
