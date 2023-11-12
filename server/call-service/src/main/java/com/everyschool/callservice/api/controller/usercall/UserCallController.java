package com.everyschool.callservice.api.controller.usercall;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.client.VoiceAiServiceClient;
import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.usercall.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStopRequest;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.usercall.UserCallAnalysisService;
import com.everyschool.callservice.api.service.usercall.UserCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/calls")
public class UserCallController {

    private final UserCallService userCallService;


    private final VoiceAiServiceClient voiceAiServiceClient;

    private final UserCallAnalysisService userCallAnalysisService;

    /**
     * 통화 녹음 시작 API
     * 상대방이 전화 알림을 통해 전화 받을시
     *
     * @param request 통화 녹음에 필요한 정보들
     * @return 녹음 시작
     */
    @PostMapping("/record/start")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecordStartInfo> createRecordStart(@RequestBody RecordStartRequest request) {
        log.debug("UserCall UserCallController#createRecordStart");
        log.debug("RecordStartRequest={}", request);

        RecordStartInfo res = voiceAiServiceClient.recordStart(request);
        log.debug("RecordStartInfo={}", res);

        return ApiResponse.created(res);
    }

    /**
     * 통화 녹음 종료 API
     *
     * @param request 통화 녹음에 필요한 정보들
     * @return 종료 정보
     */
    @PostMapping("/record/stop")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecordStopInfo> createRecordStop(@RequestBody RecordStopRequest request,
                                                        @RequestHeader("Authorization") String token) {
        log.debug("UserCall UserCallController#createRecordStop");
        log.debug("RecordStopRequest={}", request);
        log.debug("token={}", token);

        RecordStopInfo res = voiceAiServiceClient.recordStop(request);
        log.debug("RecordStopInfo={}", res);

        UserCallResponse userCall = userCallService.createCallInfo(request.toDto(), request.getOtherUserKey(), token);

        /**
         * 비동기 요청
         * 방금 저장된 음성 파일 분석
         */
        userCallAnalysisService.analyze(userCall.getUserCallId(), res.getFileDir());

        return ApiResponse.created(res);
    }

}
