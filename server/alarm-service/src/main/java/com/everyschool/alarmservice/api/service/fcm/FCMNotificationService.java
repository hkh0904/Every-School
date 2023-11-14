package com.everyschool.alarmservice.api.service.fcm;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    /**
     * 알림 요청
     *
     * @param dto 상대방 정보
     * @return 알림 요청 성공 메시지
     */
    public String sendNotification(List<String> fcmTokens, String title, String content, String type, String senderName) throws FirebaseMessagingException {
        log.debug("call FCMNotificationService#sendNotification");
        log.debug("fcmTokens = {}", fcmTokens);

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        MulticastMessage message = MulticastMessage .builder()
                .setNotification(notification)
                .putData("type", type)
                .putData("sender", senderName)
                .addAllTokens(fcmTokens)
                .build();

        BatchResponse response = firebaseMessaging.sendEachForMulticast(message);

        return response.getSuccessCount() + " messages were sent successfully";
    }
}
