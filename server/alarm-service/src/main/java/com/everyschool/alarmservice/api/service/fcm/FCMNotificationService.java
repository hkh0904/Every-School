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
    public String sendMultiNotification(List<String> fcmTokens, String title, String content, String type, String senderName) throws FirebaseMessagingException {
        log.debug("call FCMNotificationService#sendMultiNotification");
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

    public String sendNotification(String fcmToken, String title, String content, String type, Long objectId) throws FirebaseMessagingException {
        log.debug("call FCMNotificationService#sendMultiNotification");
        log.debug("fcmTokens = {}", fcmToken);

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .putData("type", type)
                .putData(type, objectId.toString())
                .build();

        String response = firebaseMessaging.send(message);

        return response + " messages were sent successfully";
    }
}
