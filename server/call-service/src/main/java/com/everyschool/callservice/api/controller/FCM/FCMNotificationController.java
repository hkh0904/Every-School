package com.everyschool.callservice.api.controller.FCM;


import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.FCM.request.CallDeniedRequest;
import com.everyschool.callservice.api.controller.FCM.request.OtherUserFcmRequest;
import com.everyschool.callservice.api.service.FCM.FCMNotificationService;
import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/call-service/v1/calls")
public class FCMNotificationController {

    private final FCMNotificationService fcmNotificationService;

    /**
     * 전화 알림 전송 API
     *
     * @param request 통화 걸 상대방의 정보
     * @return 생성 완료 메세지
     */
    @PostMapping("/calling")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> sendNotificationByToken(@RequestBody OtherUserFcmRequest request) throws FirebaseMessagingException {
        log.debug("call FCMNotificationController#sendNotificationByToken");
        log.debug("search request = {}", request);

        OtherUserFcmDto dto = request.toDto();

        return ApiResponse.created(fcmNotificationService.sendNotificationByToken(dto));
    }

    /**
     * 발신자가 전화 끊기 요청 API
     *
     * @param request 통화 건 상대방 정보와 시간
     * @return 부재중 저장 완료
     *
     */
    @PostMapping("/calling/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createUserCallClosed(@RequestBody CallDeniedRequest request,
                                                    @RequestHeader("Authorization") String token) throws FirebaseMessagingException {

        Boolean result = fcmNotificationService.createUserCallCancel(request.toDto(), token);

        if (result) {
            return ApiResponse.created("통화 취소");
        }
        return ApiResponse.created("오류 발생");
    }

    /**
     * 부재중 요청 API
     *
     * @param request 통화 건 상대방 정보와 시간
     * @return 부재중 저장 완료
     *
     */
    @PostMapping("/calling/denied")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createUserCallDenied(@RequestBody CallDeniedRequest request,
                                                    @RequestHeader("Authorization") String token){

        Boolean result = fcmNotificationService.createUserCallDenied(request.toDto(), token);

        if (result) {
            return ApiResponse.created("부재중 생성완료");
        }
        return ApiResponse.created("실패");
    }
}