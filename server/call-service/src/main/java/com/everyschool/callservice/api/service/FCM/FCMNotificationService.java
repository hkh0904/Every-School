package com.everyschool.callservice.api.service.FCM;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.controller.FCM.request.OtherUserFcmRequest;
import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserServiceClient userServiceClient;

    public String sendNotificationByToken(OtherUserFcmDto dto) throws FirebaseMessagingException {
//        String fcmToken = userServiceClient.searchUserFcmByUserKey(dto.getOtherUserKey());

//        if (fcmToken == null) {
//            throw new NoSuchElementException("상대방이 로그인 되어 있지 않습니다.");
//        }

        Notification notification = Notification.builder()
                .setTitle(dto.getSenderName())
                .setBody("calling...")
                .build();

        Message message = Message.builder()
//                .setToken(fcmToken)
                .setToken("ehfEDZqQQrSjVNTd8O1Ur1:APA91bHX2xyspcdF01k5BNlfe9tF6bsw23m0zu38As_doANeFbaG3vAFMWsanc3XgL4NZEbYbZ883Hami54Y6NwOXHzjUKomRp2qqL3XzeGGjGzdirxEfeF4-QwPjivY4V4xeOyVmmuM")
                .setNotification(notification)
                .putData("type", "call")
                .putData("cname", dto.getCname())
                .build();

        return firebaseMessaging.send(message);

//        return "전화 알림 전송 완료";
    }
}
