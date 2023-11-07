package com.everyschool.callservice.api.controller.FCM;


import com.everyschool.callservice.api.controller.FCM.request.OtherUserFcmRequest;
import com.everyschool.callservice.api.service.FCM.FCMNotificationService;
import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String sendNotificationByToken(@RequestBody OtherUserFcmRequest request) throws FirebaseMessagingException {
        OtherUserFcmDto dto = request.toDto();
        return fcmNotificationService.sendNotificationByToken(dto);
    }
}